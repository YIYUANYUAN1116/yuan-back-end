package com.yuan.common.core.utils;

import java.util.*;
import java.util.function.Function;

/**
 * 树形结构构建工具类
 *
 * 节点类型（如 SysMenu, Dept, Category 等）
 */

public class CommonTreeUtils  {

    /**
     * 将扁平列表构建成树形结构
     *
     * @param list       扁平数据列表
     * @param idGetter   获取节点 ID 的函数（如 SysMenu::getMenuId）
     * @param parentIdGetter 获取父节点 ID 的函数（如 SysMenu::getParentId）
     * @param childrenSetter 设置子节点列表的函数（如 SysMenu::setChildren）
     * @param rootParentId 根节点的 parentId 值（如 0L, null）
     * @param <T>        节点类型
     * @param <ID>       ID 类型（如 Long, Integer, String）
     * @return 树形结构的根节点列表
     */
    public static <T, ID> List<T> buildTree(
            List<T> list,
            Function<T, ID> idGetter,
            Function<T, ID> parentIdGetter,
            java.util.function.BiConsumer<T, List<T>> childrenSetter,
            ID rootParentId) {

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        // 构建 ID -> Node 的映射
        Map<ID, T> nodeMap = new HashMap<>();
        Map<ID, List<T>> childrenMap = new HashMap<>();

        for (T node : list) {
            ID id = idGetter.apply(node);
            if (id != null) {
                nodeMap.put(id, node);
                // 初始化每个节点的子节点列表
                childrenMap.put(id, new ArrayList<>());
            }
        }

        List<T> roots = new ArrayList<>();

        // 第二次遍历：建立父子关系
        for (T node : list) {
            ID id = idGetter.apply(node);
            ID parentId = parentIdGetter.apply(node);

            if (Objects.equals(parentId, rootParentId) || parentId == null) {
                // 是根节点
                roots.add(node);
            } else {
                T parent = nodeMap.get(parentId);
                if (parent != null) {
                    childrenMap.get(parentId).add(node);
                } else {
                    // 父节点不存在，视为根节点（避免数据丢失）
                    roots.add(node);
                }
            }
        }

        // 设置 children
        for (Map.Entry<ID, List<T>> entry : childrenMap.entrySet()) {
            T node = nodeMap.get(entry.getKey());
            if (node != null) {
                List<T> children = entry.getValue();
                // ✅ 修改处：判断集合是否为空，为空传 null，否则传 children 列表
                // 这样前端拿到 null 就不会显示展开图标，能极大提升性能
                childrenSetter.accept(node, (children == null || children.isEmpty()) ? null : children);
            }
        }

        return roots;
    }

}
