package com.yuan.workflow.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yuan.common.core.enums.BaseEnum;
import com.yuan.common.core.exception.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TaskType implements BaseEnum {
    NORMAL("NORMAL", "正常"),
    ADD_SIGN("ADD_SIGN", "加签");


    @EnumValue
    private final String code;
    private final String desc;

    public static TaskType fromValue(String value) {
        return Arrays.stream(values())
                .filter(a -> a.code.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new BaseException("非法操作类型: " + value));
    }
}
