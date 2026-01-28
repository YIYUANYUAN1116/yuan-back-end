package com.yuan.workflow.core.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.workflow.core.evaluator.SimpleConditionEvaluator;
import com.yuan.workflow.core.exception.ProcessDefinitionParseException;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.domain.vo.WfNodeInstanceVo;
import com.yuan.workflow.model.logicflow.LfEdge;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public List<LfNode> getNextNode(WfDefinition def, LfNode currentNode, Map<String, Object> variables) {
        LfGraph json = parse(def);

        // 1. 找出所有出边
        List<LfEdge> outEdges = json.getEdges().stream()
                .filter(e -> e.getSourceNodeId().equals(currentNode.getId()))
                .toList();

        if (outEdges.isEmpty()) {
            return null; // 无后续，流程结束
        }


        // 2. 非网关：只有一条出边
        if (!isGateway(currentNode)) {
            return List.of(getNode(def, outEdges.get(0).getTargetNodeId()));
        }

        // 3. 网关：按条件判断
        List<LfNode> matched = new ArrayList<>();
        for (LfEdge edge : outEdges) {
            if (SimpleConditionEvaluator.match(edge.getProperties().getCondition(), variables)) {
                matched.add(getNode(def, edge.getTargetNodeId()));
            }
        }

        if (matched.isEmpty()) {
            throw new ProcessDefinitionParseException(
                    WorkflowErrorCode.WF_DEFINITION_NO_DEFAULT_GATEWAY,
                    def.getId(),
                    def.getVersion()
            );
        }

        return matched;
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

    public List<WfNodeInstanceVo> parse(LfGraph graph) {
        if (graph == null || graph.getNodes() == null) {
            return Collections.emptyList();
        }
        List<LfNode> nodes = graph.getNodes();
        List<LfEdge> edges = graph.getEdges();

        Map<String, WfNodeInstanceVo> nodeMap = nodes.stream().collect(Collectors.toMap(LfNode::getId,
                FlowParser::convertNode,
                (a, b) -> a));

        nodeMap.values().forEach(node -> {
            node.setPrevNodeKeys(new ArrayList<>());
            node.setNextNodeKeys(new ArrayList<>());
        });

        // 获取边界关系
        for (LfEdge edge : edges) {
            WfNodeInstanceVo from  = nodeMap.get(edge.getSourceNodeId());
            WfNodeInstanceVo to  = nodeMap.get(edge.getTargetNodeId());
            if (from != null && to != null){
                from.getNextNodeKeys().add(to.getNodeKey());
                to.getPrevNodeKeys().add(from.getNodeKey());
            }
        }


        // 按流程顺序排序（从 start 开始）
        return sortByFlowOrder(nodeMap);
    }

    private List<WfNodeInstanceVo> sortByFlowOrder(Map<String, WfNodeInstanceVo> nodeMap) {
        // 找 start 节点
        Optional<WfNodeInstanceVo> startOpt = nodeMap.values().stream()
                .filter(n -> NodeType.START.equals(n.getNodeType()))
                .findFirst();

        if (startOpt.isEmpty()) {
            // 没 start，就按插入顺序
            int i = 0;
            for (WfNodeInstanceVo n : nodeMap.values()) {
                n.setOrderNo(i++);
            }
            return new ArrayList<>(nodeMap.values());
        }

        List<WfNodeInstanceVo> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        dfs(startOpt.get(), nodeMap, visited, result);

        // 设置 order
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setOrderNo(i);
        }
        return result;
    }

    private void dfs(WfNodeInstanceVo node, Map<String, WfNodeInstanceVo> nodeMap, Set<String> visited, List<WfNodeInstanceVo> result) {
        if (!visited.add(node.getNodeKey())) return;

        result.add(node);

        for (String nextNodeKey : node.getNextNodeKeys()) {
            WfNodeInstanceVo nextNode = nodeMap.get(nextNodeKey);
            if (nextNode != null) {
                dfs(nextNode, nodeMap, visited, result);
            }
        }
    }

    private static WfNodeInstanceVo convertNode(LfNode node) {
        WfNodeInstanceVo vo = new WfNodeInstanceVo();
        vo.setNodeKey(node.getId());
        vo.setNodeName(node.getText().getValue());
        vo.setNodeType(NodeType.valueOf(node.getProperties().getWfType()));
        vo.setStatus(NodeStatus.NOT_REACHED);
        return vo;
    }

    public boolean isGateway(LfNode node) {
        return NodeType.GATEWAY.getCode()
                .equals(node.getProperties().getWfType());
    }

    public boolean isUserTask(LfNode node) {
        return NodeType.USER_TASK.getCode()
                .equals(node.getProperties().getWfType());
    }

    public boolean isEnd(LfNode node) {
        return NodeType.END.getCode()
                .equals(node.getProperties().getWfType());
    }

    public boolean isSystem(LfNode node) {
        return NodeType.SYSTEM_TASK.getCode()
                .equals(node.getProperties().getWfType());
    }

}
