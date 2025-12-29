package com.yuan.workflow.api.enums;

import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程为什么结束
 */
@Getter
@AllArgsConstructor
public enum WfEndReason implements BaseEnum {
    APPROVED("APPROVED","正常流转通过导致结束"),    // 正常流转通过导致结束
    REJECTED("REJECTED","驳回导致结束"),    // 驳回导致结束（如果你的引擎支持“驳回即结束”）
    WITHDRAWN("WITHDRAWN","撤回导致结束"),   // 撤回导致结束
    TERMINATED("TERMINATED","管理员强制终止");   // 管理员强制终止（预留）

    private final String code;
    private final String desc;
}