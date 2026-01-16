package com.yuan.common.core.bizno;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizNoPrefixEnum {

    OA("OA", "OA-请假/办公"),
    EXP("EXP", "报销单"),
    CON("CON", "合同"),
    PUR("PUR", "采购单"),
    WF("WF", "流程实例");

    private final String prefix;
    private final String desc;
}
