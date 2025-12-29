package com.yuan.workflow.api.enums;

import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VariableType implements BaseEnum {

    STRING("STRING", "字符串"),
    NUMBER("NUMBER", "数字"),
    BOOLEAN("BOOLEAN", "布尔"),
    DATE("DATE", "日期");

    private final String code;
    private final String desc;
}
