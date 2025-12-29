package com.yuan.workflow.api.enums;

import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeStatus implements BaseEnum {

    WAIT("WAIT", "待执行"),
    DONE("DONE", "已完成"),
    CANCELED("CANCELED", "已取消");
    private final String code;
    private final String desc;
}
