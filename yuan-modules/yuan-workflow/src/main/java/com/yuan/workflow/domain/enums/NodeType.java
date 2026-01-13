package com.yuan.workflow.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeType implements BaseEnum {

    START("START", "开始节点"),
    APPROVAL("APPROVAL", "审批节点"),
    GATEWAY("GATEWAY", "条件网关"),
    END("END", "结束节点");

    @EnumValue
    private final String code;
    private final String desc;
}