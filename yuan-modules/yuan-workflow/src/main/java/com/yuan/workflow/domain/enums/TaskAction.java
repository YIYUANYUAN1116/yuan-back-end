package com.yuan.workflow.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yuan.common.core.enums.BaseEnum;
import com.yuan.common.core.exception.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TaskAction implements BaseEnum {

    ANY_APPROVE("ANY_APPROVE", "或签同意"),
    REJECT("REJECT", "驳回"),
    ROLLBACK("ROLLBACK", "退回"),
    TRANSFER("TRANSFER", "转签"),
    ADD_SIGN("ADD_SIGN", "加签"),
    ALL_APPROVE("ALL_APPROVE","会签同意"),
    WITHDRAW("WITHDRAW", "撤回"),
    SYSTEM_PROCESS("SYSTEM_PROCESS", "系统处理"),;

    @EnumValue
    private final String code;
    private final String desc;

    public static TaskAction fromValue(String value) {
        return Arrays.stream(values())
                .filter(a -> a.code.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new BaseException("非法操作类型: " + value));
    }
}
