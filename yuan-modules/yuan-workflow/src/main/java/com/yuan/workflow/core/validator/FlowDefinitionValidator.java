package com.yuan.workflow.core.validator;

import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.model.logicflow.LfEdge;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class FlowDefinitionValidator {

    // 允许的节点类型（你可以扩展）
    private static final Set<NodeType> ALLOWED_NODE_TYPES =
            Set.of(NodeType.START, NodeType.APPROVAL, NodeType.GATEWAY, NodeType.END);

    public FlowValidationResult validateForPublish(LfGraph def) {
        List<String> errors = new ArrayList<>();

        if (def == null) {
            return FlowValidationResult.fail(List.of("flow_json 不能为空"));
        }

        // 1) 基础：节点/边不能为空
        if (def.getNodes() == null || def.getNodes().isEmpty()) {
            errors.add("nodes 不能为空");
            return FlowValidationResult.fail(errors);
        }
        if (def.getEdges() == null || def.getEdges().isEmpty()) {
            errors.add("edges 不能为空");
            return FlowValidationResult.fail(errors);
        }

        // 2) 节点合法性（id/type、重复、未知类型）
        Map<String, LfNode> nodeMap = new HashMap<>();
        for (LfNode n : def.getNodes()) {
            if (n == null) {
                errors.add("存在空节点对象");
                continue;
            }
            if (!StringUtils.hasText(n.getId())) {
                errors.add("存在节点 id 为空");
                continue;
            }
            if (n.getProperties().getWfType() == null) {
                errors.add("节点[" + n.getId() + "] type 为空");
                continue;
            }
            if (!ALLOWED_NODE_TYPES.contains(n.getProperties().getWfType())) {
                errors.add("节点[" + n.getId() + "] type 不支持: " + n.getProperties().getWfType());
            }
            if (nodeMap.containsKey(n.getId())) {
                errors.add("节点 id 重复: " + n.getId());
            } else {
                nodeMap.put(n.getId(), n);
            }
        }

        if (!errors.isEmpty()) {
            return FlowValidationResult.fail(errors);
        }

        // 3) start/end 规则：必须且只能 1 个
        List<LfNode> starts = nodeMap.values().stream()
                .filter(n -> NodeType.START.getCode().equals(n.getProperties().getWfType()))
                .toList();
        List<LfNode> ends = nodeMap.values().stream()
                .filter(n -> NodeType.END.getCode().equals(n.getProperties().getWfType()))
                .toList();

        if (starts.size() != 1) errors.add("start 节点必须且只能 1 个，当前: " + starts.size());
        if (ends.size() != 1) errors.add("end 节点必须且只能 1 个，当前: " + ends.size());
        if (!errors.isEmpty()) return FlowValidationResult.fail(errors);

        String startId = starts.get(0).getId();
        String endId = ends.get(0).getId();

        // 4) 边合法性（from/to 必须存在、不能自环、重复边）
        Set<String> edgeUniq = new HashSet<>();
        for (LfEdge e : def.getEdges()) {
            if (e == null) {
                errors.add("存在空边对象");
                continue;
            }
            if (!StringUtils.hasText(e.getSourceNodeId()) || !StringUtils.hasText(e.getTargetNodeId())) {
                errors.add("存在边 from/to 为空");
                continue;
            }
            if (!nodeMap.containsKey(e.getSourceNodeId())) {
                errors.add("边 from 节点不存在: " + e.getSourceNodeId());
            }
            if (!nodeMap.containsKey(e.getTargetNodeId())) {
                errors.add("边 to 节点不存在: " + e.getTargetNodeId());
            }
            if (e.getSourceNodeId().equals(e.getTargetNodeId())) {
                errors.add("存在自环边: " + e.getSourceNodeId() + " -> " + e.getTargetNodeId());
            }
            String key = e.getSourceNodeId() + "->" + e.getTargetNodeId();
            if (!edgeUniq.add(key)) {
                errors.add("重复边: " + key);
            }
        }
        if (!errors.isEmpty()) return FlowValidationResult.fail(errors);

        // 5) 构建邻接表
        Map<String, List<String>> nextMap = new HashMap<>();
        Map<String, List<String>> prevMap = new HashMap<>();
        for (String id : nodeMap.keySet()) {
            nextMap.put(id, new ArrayList<>());
            prevMap.put(id, new ArrayList<>());
        }
        for (LfEdge e : def.getEdges()) {
            nextMap.get(e.getSourceNodeId()).add(e.getTargetNodeId());
            prevMap.get(e.getTargetNodeId()).add(e.getSourceNodeId());
        }

        // 6) 结构约束（可选但强烈推荐）
        // start：必须有至少1条出边，且不能有入边
        if (nextMap.get(startId).isEmpty()) errors.add("start 节点必须有出边");
        if (!prevMap.get(startId).isEmpty()) errors.add("start 节点不能有入边");

        // end：必须有入边，且不能有出边
        if (prevMap.get(endId).isEmpty()) errors.add("end 节点必须有入边");
        if (!nextMap.get(endId).isEmpty()) errors.add("end 节点不能有出边");

        // 除 start/end 外：不能完全孤立
        for (String id : nodeMap.keySet()) {
            if (id.equals(startId) || id.equals(endId)) continue;
            if (prevMap.get(id).isEmpty() && nextMap.get(id).isEmpty()) {
                errors.add("节点[" + id + "] 孤立（无入边无出边）");
            }
        }

        if (!errors.isEmpty()) return FlowValidationResult.fail(errors);

        // 7) 可达性：从 start 必须能到达所有业务节点（至少 end）
        Set<String> reachableFromStart = bfs(startId, nextMap);
        if (!reachableFromStart.contains(endId)) {
            errors.add("从 start 无法到达 end（流程不会结束）");
        }

        // 找不可达节点（通常是设计器遗留节点）
        List<String> unreachable = nodeMap.keySet().stream()
                .filter(id -> !reachableFromStart.contains(id))
                .sorted()
                .toList();
        if (!unreachable.isEmpty()) {
            errors.add("存在不可达节点: " + String.join(", ", unreachable));
        }

        if (!errors.isEmpty()) return FlowValidationResult.fail(errors);

        // 8) 终止性：每个可达节点必须能到达 end（避免走入死胡同）
        Set<String> canReachEnd = reverseBfs(endId, prevMap);
        List<String> deadEndNodes = reachableFromStart.stream()
                .filter(id -> !canReachEnd.contains(id))
                .sorted()
                .toList();
        if (!deadEndNodes.isEmpty()) {
            errors.add("存在无法到达 end 的节点（死胡同）: " + String.join(", ", deadEndNodes));
            return FlowValidationResult.fail(errors);
        }

        // 9) 防死循环：检测环（最重要）
        // 只对“从 start 可达”的子图做环检测，避免无关节点干扰
        List<String> cyclePath = detectCyclePath(reachableFromStart, nextMap);
        if (!cyclePath.isEmpty()) {
            errors.add("检测到流程环（可能死循环）: " + String.join(" -> ", cyclePath));
            return FlowValidationResult.fail(errors);
        }

        return FlowValidationResult.ok();
    }

    private Set<String> bfs(String start, Map<String, List<String>> nextMap) {
        Set<String> visited = new HashSet<>();
        Deque<String> q = new ArrayDeque<>();
        q.add(start);
        visited.add(start);
        while (!q.isEmpty()) {
            String cur = q.poll();
            for (String nxt : nextMap.getOrDefault(cur, List.of())) {
                if (visited.add(nxt)) q.add(nxt);
            }
        }
        return visited;
    }

    private Set<String> reverseBfs(String end, Map<String, List<String>> prevMap) {
        Set<String> visited = new HashSet<>();
        Deque<String> q = new ArrayDeque<>();
        q.add(end);
        visited.add(end);
        while (!q.isEmpty()) {
            String cur = q.poll();
            for (String prev : prevMap.getOrDefault(cur, List.of())) {
                if (visited.add(prev)) q.add(prev);
            }
        }
        return visited;
    }

    /**
     * 检测环，并返回一条“可读的环路径”（便于前端/操作者定位）
     */
    private List<String> detectCyclePath(Set<String> subGraphNodes,
                                         Map<String, List<String>> nextMap) {

        Map<String, Integer> state = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        for (String n : subGraphNodes) state.put(n, 0); // 0=未访问 1=访问中 2=完成

        for (String n : subGraphNodes) {
            if (state.get(n) == 0) {
                List<String> cycle = dfsFindCycle(n, subGraphNodes, nextMap, state, parent);
                if (!cycle.isEmpty()) return cycle;
            }
        }
        return List.of();
    }

    private List<String> dfsFindCycle(String u,
                                      Set<String> subGraphNodes,
                                      Map<String, List<String>> nextMap,
                                      Map<String, Integer> state,
                                      Map<String, String> parent) {
        state.put(u, 1);
        for (String v : nextMap.getOrDefault(u, List.of())) {
            if (!subGraphNodes.contains(v)) continue;

            if (state.get(v) == 0) {
                parent.put(v, u);
                List<String> cycle = dfsFindCycle(v, subGraphNodes, nextMap, state, parent);
                if (!cycle.isEmpty()) return cycle;
            } else if (state.get(v) == 1) {
                // u -> v 形成回边，构造环路径
                return buildCyclePath(u, v, parent);
            }
        }
        state.put(u, 2);
        return List.of();
    }

    private List<String> buildCyclePath(String from, String to, Map<String, String> parent) {
        // 从 from 回溯到 to
        List<String> path = new ArrayList<>();
        path.add(to);
        String cur = from;
        path.add(cur);
        while (cur != null && !cur.equals(to)) {
            cur = parent.get(cur);
            if (cur != null) path.add(cur);
        }
        Collections.reverse(path);
        // 补一个 to 形成闭环展示
        path.add(to);
        return path;
    }
}

