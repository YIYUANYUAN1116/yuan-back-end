package com.yuan.workflow.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yuan.common.core.enums.BaseEnum;
import com.yuan.common.core.exception.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeType implements BaseEnum {

    START("START", "开始节点"),
    USER_TASK("USER_TASK", "审批节点"),
    SYSTEM_TASK("SYSTEM_TASK", "系统审批"),
    GATEWAY("GATEWAY", "条件网关"),
    END("END", "结束节点");

    @EnumValue
    private final String code;
    private final String desc;

    public static NodeType of(String code) {
        for (NodeType nodeType : NodeType.values()) {
            if (nodeType.getCode().equals(code)) {
                return nodeType;
            }
        }
        throw new BaseException("非法节点类型: " + code);
    }
}