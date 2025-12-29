package com.yuan.workflow.api.enums;

import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskAction implements BaseEnum {

    APPROVE("APPROVE", "同意"),
    REJECT("REJECT", "驳回"),
    TRANSFER("TRANSFER", "转签"),
    ADD_SIGN("ADD_SIGN", "加签"),
    WITHDRAW("WITHDRAW", "撤回");

    private final String code;
    private final String desc;
}
