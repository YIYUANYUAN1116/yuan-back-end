package com.yuan.workflow.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstanceStatus implements BaseEnum {

    RUNNING("RUNNING", "进行中"),
    APPROVED("APPROVED", "已通过"),
    REJECTED("REJECTED", "已驳回"),
    CANCELED("CANCELED", "已撤销");

    @EnumValue
    private final String code;
    private final String desc;
}