package com.yuan.workflow.domain.enums;

import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConditionOperator implements BaseEnum {

    EQ("EQ", "="),
    NE("NE", "!="),
    GT("GT", ">"),
    GE("GE", ">="),
    LT("LT", "<"),
    LE("LE", "<="),
    IN("IN", "包含"),
    NOT_IN("NOT_IN", "不包含");

    private final String code;
    private final String desc;
}
