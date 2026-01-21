package com.yuan.workflow.core.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.core.evaluator.SimpleConditionEvaluator;
import com.yuan.workflow.core.exception.ProcessDefinitionParseException;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.model.Expression;
import com.yuan.workflow.model.logicflow.LfEdge;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.model.logicflow.LfProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
                .orElseThrow(() -> {
                    log.error("[getStartNode] error, start node not found,definitionId={}, name={}, version={}", def.getId(), def.getDefinitionName(), def.getVersion());
                    return  new ProcessDefinitionParseException(
                            WorkflowErrorCode.WF_DEFINITION_NO_START_NODE,
                            def.getId(),
                            def.getVersion()
                    );
                });
    }


    public LfNode getNode(WfDefinition def, String nodeKey) {
        LfGraph json = parse(def);
        return json.getNodes().stream()
                .filter(n-> Objects.equals(n.getId(), nodeKey))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("[getNode] error, node not found: definitionId={}, name={}, version={},nodeId={}", def.getId(), def.getDefinitionName(), def.getVersion(),nodeKey);
                    return  new ProcessDefinitionParseException(
                            WorkflowErrorCode.WF_DEFINITION_NODE_NOT_FOUND,
                            def.getId(),
                            def.getVersion(),
                            nodeKey
                    );
                });
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

        log.error(
                "Gateway node [{}] matched no condition and has no default target",
                currentNode.getId()
        );
        throw new ProcessDefinitionParseException(
                WorkflowErrorCode.WF_DEFINITION_NO_DEFAULT_GATEWAY,
                def.getId(),
                def.getVersion()
        );
    }


    public LfGraph parse(WfDefinition def) {
        String key = def.getId()+"-"+def.getVersion();
        return cache.computeIfAbsent(key, id -> {
            try {
                return objectMapper.readValue(
                        def.getFlowJson(), LfGraph.class);
            } catch (Exception e) {
                log.error(
                        "Failed to parse workflow definition. definitionId={}, name={}, version={}",
                        def.getId(),
                        def.getDefinitionName(),
                        def.getVersion(),
                        e
                );
                throw new ProcessDefinitionParseException(def.getId(),def.getVersion());
            }
        });
    }
}
