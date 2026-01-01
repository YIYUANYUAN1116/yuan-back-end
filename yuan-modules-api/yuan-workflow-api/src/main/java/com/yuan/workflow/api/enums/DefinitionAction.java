package com.yuan.workflow.api.enums;

import com.yuan.common.core.enums.BaseEnum;
import com.yuan.common.core.exception.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DefinitionAction implements BaseEnum {

    PUBLISH("publish", "发布流程"),
    DISABLE("disable", "停用流程");

    private final String code;
    private final String desc;

    public static DefinitionAction fromValue(String value) {
        return Arrays.stream(values())
                .filter(a -> a.code.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new BaseException("非法操作类型: " + value));
    }
}