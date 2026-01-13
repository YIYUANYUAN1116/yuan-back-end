package com.yuan.workflow.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssigneeType implements BaseEnum {

    USER("USER", "指定用户"),
    ROLE("ROLE", "角色"),
    DEPT("DEPT", "部门"),
    STARTER("STARTER", "流程发起人"),
    STARTER_MANAGER("STARTER_MANAGER", "发起人上级");

    @EnumValue
    private final String code;
    private final String desc;
}
