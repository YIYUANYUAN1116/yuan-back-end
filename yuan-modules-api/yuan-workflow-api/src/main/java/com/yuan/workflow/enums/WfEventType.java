package com.yuan.workflow.enums;

import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发生了什么
 */
@Getter
@AllArgsConstructor
public enum WfEventType implements BaseEnum {

    // 任务相关
    TASK_APPROVED("TASK_APPROVED","创建"),
    TASK_REJECTED("TASK_REJECTED","分配"),
    TASK_ROLLBACK_PREV("TASK_ROLLBACK_PREV","退回上个节点"),
    TASK_ROLLBACK_TO("TASK_ROLLBACK_TO","退回指定节点"), // 审批/拒绝/退回（本质都是对task做decision）
    TASK_TRANSFERRED("TASK_TRANSFERRED","转办"),  // 转办

    // 实例相关
    INSTANCE_STARTED("INSTANCE_STARTED","开始"),
    INSTANCE_WITHDRAWN("INSTANCE_WITHDRAWN","撤回"), // 撤回（一般是发起人撤回）
    INSTANCE_ENDED("INSTANCE_ENDED","正常/异常结束"),     // 正常/异常结束
    INSTANCE_TERMINATED("INSTANCE_TERMINATED","管理员强制终止（可选）"); // 管理员强制终止（可选）

    private final String code;
    private final String desc;
}
