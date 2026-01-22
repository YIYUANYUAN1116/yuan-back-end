package com.yuan.workflow.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeStatus implements BaseEnum {

    //运行层状态
    WAIT("WAIT", "待执行"),
    DONE("DONE", "已完成"),
    CANCELED("CANCELED", "已取消"),

    //定义层状态
    NOT_REACHED("NOT_REACHED","未到达");

    @EnumValue
    private final String code;
    private final String desc;
}
