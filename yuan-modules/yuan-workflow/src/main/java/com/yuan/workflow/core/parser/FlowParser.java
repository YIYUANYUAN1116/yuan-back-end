package com.yuan.workflow.core.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.workflow.api.enums.NodeType;
import com.yuan.workflow.core.evaluator.SimpleConditionEvaluator;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.model.Expression;
import com.yuan.workflow.model.logicflow.LfEdge;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.model.logicflow.LfProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class FlowParser {
    private final ObjectMapper objectMapper;
    /** 缓存解析结果 */
    private final Map<String, LfGraph> cache = new ConcurrentHashMap<>();

    public LfNode getStartNode(WfDefinition def) {
        LfGraph json = parse(def);
        return json.getNodes().stream()
                .filter(n-> Objects.equals(n.getProperties().getWfType(), NodeType.START.getCode()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("未找到 START 节点"));
    }


    public LfNode getNode(WfDefinition def, String nodeKey) {
        LfGraph json = parse(def);
        return json.getNodes().stream()
                .filter(n-> Objects.equals(n.getId(), nodeKey))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("节点不存在: " + nodeKey));
    }

    public LfNode getNextNode(WfDefinition def, LfNode currentNode, Map<String, Object> variables) {
        LfGraph json = parse(def);

        // 1. 找出所有出边
        List<LfEdge> outEdges = json.getEdges().stream()
                .filter(e -> e.getSourceNodeId().equals(currentNode.getId()))
                .toList();

        if (outEdges.isEmpty()) {
            return null; // 无后续，流程结束
        }


        // 2. 非网关：只有一条出边
        if (!Objects.equals(currentNode.getProperties().getWfType(), NodeType.GATEWAY.getCode())) {
            String nextId = outEdges.get(0).getTargetNodeId();
            LfNode node = getNode(def, nextId);
            if (node.getProperties().getWfType().equals(NodeType.GATEWAY.getCode())) {
                //下一个节点时网关时，继续找网关的下一个节点
                return getNextNode(def, node, variables);
            }
            return node;
        }

        // 3. 网关：按条件判断
        for (LfEdge outEdge : outEdges) {
            LfProperties properties = outEdge.getProperties();
            Expression condition = properties.getCondition();
            if (SimpleConditionEvaluator.match(condition, variables)) {
                LfNode node = getNode(def, outEdge.getTargetNodeId());
                if (node.getProperties().getWfType().equals(NodeType.GATEWAY.getCode())) {
                    //下一个节点时网关时，继续找网关的下一个节点
                    return getNextNode(def, node, variables);
                }
                return node;
            }
        }

        throw new IllegalStateException(
                "网关节点未命中任何条件，且无 defaultTarget: " + currentNode.getId());

    }


    private LfGraph parse(WfDefinition def) {
        String key = def.getId()+"-"+def.getVersion();
        return cache.computeIfAbsent(key, id -> {
            try {
                return objectMapper.readValue(
                        def.getFlowJson(), LfGraph.class);
            } catch (Exception e) {
                throw new IllegalStateException("流程 JSON 解析失败", e);
            }
        });
    }
}
