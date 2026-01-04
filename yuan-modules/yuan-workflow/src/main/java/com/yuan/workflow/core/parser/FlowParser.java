package com.yuan.workflow.core.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.workflow.api.enums.NodeType;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.model.Expression;
import com.yuan.workflow.model.logicflow.LfEdge;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
                .filter(n->n.getType() == NodeType.START)
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
        if (currentNode.getType() != NodeType.GATEWAY) {
            String nextId = outEdges.get(0).getTargetNodeId();
            return getNode(def, nextId);
        }

        // 3. 网关：按条件判断
        for (LfEdge outEdge : outEdges) {
            Map<String, Object> properties = outEdge.getProperties();
            Expression expression = (Expression) properties.get("condition");
            if (match(expression, variables)) {
                return getNode(def, outEdge.getTargetNodeId());
            }
        }

        throw new IllegalStateException(
                "网关节点未命中任何条件，且无 defaultTarget: " + currentNode.getId());

    }

    private boolean match(Expression exp, Map<String, Object> vars) {
        Object actual = vars.get(exp.getField());
        Object expected = exp.getValue();

        if (actual == null) {
            return false;
        }

        return switch (exp.getOperator()) {
            case EQ -> Objects.equals(actual, expected);
            case GT -> ((Number) actual).doubleValue() > Double.parseDouble(expected.toString());
            case GE -> ((Number) actual).doubleValue() >= Double.parseDouble(expected.toString());
            case LT -> ((Number) actual).doubleValue() < Double.parseDouble(expected.toString());
            case LE -> ((Number) actual).doubleValue() <= Double.parseDouble(expected.toString());
            case IN -> ((Collection<?>) expected).contains(actual);
            case NOT_IN -> !((Collection<?>) expected).contains(actual);
            case NE -> !Objects.equals(actual, expected);
        };

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
