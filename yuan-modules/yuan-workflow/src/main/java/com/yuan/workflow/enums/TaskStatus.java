package com.yuan.workflow.enums;

import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus implements BaseEnum {

    TODO("TODO", "待处理"),
    DONE("DONE", "已完成"),
    TRANSFERRED("TRANSFERRED", "已转签"),
    CANCELED("CANCELED", "已取消");

    private final String code;
    private final String desc;
}
