package com.yuan.workflow.enums;

import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审批判断结果是什么
 */
@Getter
@AllArgsConstructor
public enum WfDecision implements BaseEnum {
    APPROVED("APPROVED","通过"),
    REJECTED("REJECTED","驳回"),
    ROLLBACK_PREV("ROLLBACK_PREV","退回上一节点"),
    ROLLBACK_TO("ROLLBACK_TO","退回指定节点"),
    WITHDRAW("WITHDRAW","撤回"),
    TRANSFER("TRANSFER","转办"),;


    // 加签/减签/委派(delegate) 也可以继续扩展
    private final String code;
    private final String desc;
}