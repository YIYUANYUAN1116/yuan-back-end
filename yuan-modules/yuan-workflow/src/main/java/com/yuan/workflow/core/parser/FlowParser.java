package com.yuan.workflow.core.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.utils.StreamUtils;
import com.yuan.workflow.core.evaluator.SimpleConditionEvaluator;
import com.yuan.workflow.core.exception.ProcessDefinitionParseException;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.domain.vo.WfNodeInstanceVo;
import com.yuan.workflow.model.Expression;
import com.yuan.workflow.model.logicflow.LfEdge;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlowParser {
    private final ObjectMapper objectMapper;

    /** 缓存解析结果 */
    private final Map<String, LfGraph> cache = new ConcurrentHashMap<>();

    public LfNode getStartNode(WfDefinition def) {
        LfGraph json = parseNode(def);
        LfNode startNode = getStartNode(json.getNodes());
        if (startNode == null) {
            throw new ProcessDefinitionParseException(
                    WorkflowErrorCode.WF_DEFINITION_NO_START_NODE,
                    def.getId(),
                    def.getVersion()
            );
        }
       return startNode;
    }


    public LfNode getStartNode(List<LfNode> nodes) {
        return nodes.stream()
                .filter(n-> Objects.equals(n.getProperties().getWfType(), NodeType.START.getCode()))
                .findFirst()
                .orElse(null);
    }


    public LfNode getNode(WfDefinition def, String nodeKey) {
        LfGraph json = parseNode(def);
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
        LfGraph json = parseNode(def);

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
                LfNode node = getNode(def, edge.getTargetNodeId());
                node.getProperties().setCondition(edge.getProperties().getCondition());
                matched.add(node);
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



    public LfGraph parseNode(WfDefinition def) {
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

    public LfGraph parseSortNode(WfDefinition def) {
        LfGraph lfGraph = parseNode(def);
        sortGraph(lfGraph);
        return lfGraph;
    }

    public void sortGraph(LfGraph graph) {
        if (graph == null || graph.getNodes() == null || graph.getEdges() == null) return;
        List<LfEdge> edges = graph.getEdges();
        List<LfNode> nodes = graph.getNodes();

        // nodeId -> node
        Map<String, LfNode> nodeMap = nodes.stream()
                .collect(Collectors.toMap(LfNode::getId, n -> n));

        // 入度表
        Map<String, Integer> inDegree = new HashMap<>();
        nodes.forEach(n -> inDegree.put(n.getId(), 0));

        // 邻接表
        Map<String, List<String>> adj = new HashMap<>();

        for (LfEdge edge : edges) {
            String from = edge.getSourceNodeId();
            String to = edge.getTargetNodeId();

            adj.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
        }

        // 入度为 0 的节点（通常是 start）
        Deque<String> queue = new ArrayDeque<>();
        for (Map.Entry<String, Integer> e : inDegree.entrySet()) {
            if (e.getValue() == 0) {
                queue.offer(e.getKey());
            }
        }

        List<LfNode> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            String nodeId = queue.poll();
            result.add(nodeMap.get(nodeId));

            for (String next : adj.getOrDefault(nodeId, List.of())) {
                inDegree.put(next, inDegree.get(next) - 1);
                if (inDegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }

        // 安全校验：是否存在环
        if (result.size() != nodes.size()) {
            throw new IllegalStateException("流程图存在环，无法按顺序排序");
        }

        graph.setNodes(result);
    }


    public List<WfNodeInstanceVo> parseNode(LfGraph graph) {
        if (graph == null || graph.getNodes() == null) {
            return Collections.emptyList();
        }
        List<LfNode> nodes = graph.getNodes();
        List<LfEdge> edges = graph.getEdges();

        LfNode startNode = getStartNode(nodes);
        Map<String, LfNode> nodeMap = StreamUtils.toMap(nodes, LfNode::getId);
        Map<String, List<LfEdge>> edgeMap = edges.stream().collect(Collectors.groupingBy(LfEdge::getSourceNodeId));
        Set<String> visited = new HashSet<>();
        ArrayList<WfNodeInstanceVo> result = new ArrayList<>();
        dfs(startNode,nodeMap,edgeMap,visited,result);

        return result;
    }

    private void dfs(LfNode node, Map<String, LfNode> nodeMap,
                     Map<String, List<LfEdge>> edgeMap, Set<String> visited,
                     List<WfNodeInstanceVo> result) {
        if (node==null || visited.contains(node.getId())) return;
        visited.add(node.getId());
        result.add(convertNode(node));
        List<LfEdge> lfEdges = edgeMap.get(node.getId());
        if (lfEdges == null || lfEdges.isEmpty()) return;
        for (LfEdge lfEdge : lfEdges) {
            LfNode nextNode = nodeMap.get(lfEdge.getTargetNodeId());
            dfs(nextNode,nodeMap,edgeMap,visited,result);
        }

    }

    public List<List<LfNode>> topoLayers(LfGraph graph) {
      return topoLayers(graph.getNodes(),graph.getEdges(),byYThenX());
    }


    /**
     * 分层拓扑排序：返回 layers，每层是一批“入度已满足”的节点（可并行/同阶段）
     *
     * @param nodes      所有节点
     * @param edges      所有边（有向：source -> target）
     * @param tieBreaker 同层稳定排序规则（可为 null）
     */
    public  List<List<LfNode>> topoLayers(
            List<LfNode> nodes,
            List<LfEdge> edges,
            Comparator<LfNode> tieBreaker
    ) {
        if (nodes == null || nodes.isEmpty()) return List.of();

        // nodeId -> node
        Map<String, LfNode> nodeMap = nodes.stream()
                .collect(Collectors.toMap(LfNode::getId, Function.identity(), (a, b) -> a));

        // 入度表
        Map<String, Integer> inDegree = new HashMap<>(nodes.size() * 2);
        for (LfNode n : nodes) {
            inDegree.put(n.getId(), 0);
        }

        // 邻接表 + 构建入度
        Map<String, List<String>> adj = new HashMap<>();
        for (LfEdge e : edges) {
            String from = e.getSourceNodeId();
            String to = e.getTargetNodeId();
            if (!nodeMap.containsKey(from) || !nodeMap.containsKey(to)) {
                // 防御：边指向不存在的节点，直接跳过或抛异常都行
                continue;
            }
            adj.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            inDegree.put(to, inDegree.get(to) + 1);
        }

        // 初始层：所有入度为 0 的节点
        List<String> current = inDegree.entrySet().stream()
                .filter(e -> e.getValue() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 如果你更希望 start 在第一层（且仅一个），可以在这里优先把 type=start 的排到最前面
        // 但我不强行假设，你前端展示时也可处理。

        List<List<LfNode>> layers = new ArrayList<>();
        int visited = 0;

        while (!current.isEmpty()) {
            // 这一层的 nodeIds -> nodes
            List<LfNode> layerNodes = current.stream()
                    .map(nodeMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // 同层稳定排序
            if (tieBreaker != null) {
                layerNodes.sort(tieBreaker);
            }

            layers.add(layerNodes);
            visited += layerNodes.size();

            // 准备下一层：本层出边的 target，如果入度减到 0，则进入 next
            List<String> next = new ArrayList<>();
            for (String nodeId : current) {
                for (String to : adj.getOrDefault(nodeId, List.of())) {
                    int deg = inDegree.get(to) - 1;
                    inDegree.put(to, deg);
                    if (deg == 0) {
                        next.add(to);
                    }
                }
            }

            current = next;
        }

        // 环检测：visited != nodes.size() 说明有环或不可达结构异常
        if (visited != nodeMap.size()) {
            // 找出剩余没被输出的节点，方便定位流程配置问题
            Set<String> remain = new LinkedHashSet<>(nodeMap.keySet());
            for (List<LfNode> layer : layers) {
                for (LfNode n : layer) remain.remove(n.getId());
            }
            throw new IllegalStateException("流程图存在环或异常依赖，未能分层输出节点: " + remain);
        }

        return layers;
    }

    /**
     * 一个常用的 tieBreaker：按 y 再按 x（更符合前端从上到下、从左到右的视觉顺序）
     * 如果你的 LfNode 没有 x/y，就换成 name/serialNo。
     */
    public  Comparator<LfNode> byYThenX() {
        return Comparator
                .comparing((LfNode n) -> Optional.ofNullable(n.getY()).orElse(String.valueOf(Integer.MAX_VALUE)))
                .thenComparing(n -> Optional.ofNullable(n.getX()).orElse(String.valueOf(Integer.MAX_VALUE)))
                .thenComparing(LfNode::getId);
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

    public String parseConditionExpr(LfNode lfNode) {
        Expression condition = lfNode.getProperties().getCondition();
        if (condition == null) return null;
        String field = condition.getField();
        String desc = condition.getOperator().getDesc();
        String value = condition.getValue();
        return field + desc + value;
    }
}
