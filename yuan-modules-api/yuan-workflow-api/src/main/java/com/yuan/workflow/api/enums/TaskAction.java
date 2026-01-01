package com.yuan.workflow.api.enums;

import com.yuan.common.core.enums.BaseEnum;
import com.yuan.common.core.exception.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

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

    public static TaskAction fromValue(String value) {
        return Arrays.stream(values())
                .filter(a -> a.code.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new BaseException("非法操作类型: " + value));
    }
}
