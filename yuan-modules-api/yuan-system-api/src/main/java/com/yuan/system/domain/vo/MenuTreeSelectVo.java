package com.yuan.system.domain.vo;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

@Data
public class MenuTreeSelectVo {

    /**
     * 选中菜单列表
     */
    private List<Long> checkedKeys;

    /**
     * 菜单下拉树结构列表
     */
    private List<Tree<Long>> menus;

}