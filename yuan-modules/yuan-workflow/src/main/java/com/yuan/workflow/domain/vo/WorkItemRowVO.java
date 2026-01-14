package com.yuan.workflow.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkItemRowVO {
    private Long taskId;          // 待办/已办才有
    private Long instanceId;
    private Long nodeInstanceId;

    private String taskStatus;    // TODO/DONE/CANCELED
    private String taskAction;    // APPROVE/REJECT/ROLLBACK/WITHDRAW/TRANSFER
    private String taskComment;
    private LocalDateTime taskCreateTime;
    private LocalDateTime taskFinishTime;

    private String instanceStatus;   // RUNNING/APPROVED/REJECTED/CANCELED...
    private LocalDateTime instanceStartTime;
    private LocalDateTime instanceEndTime;
    private String instanceEndReason;
    private String instanceEndComment;

    private String nodeKey;
    private String nodeName;      // 从定义里取 or 节点实例存一份

    private String bizType;
    private Long bizId;
    private String bizNo;      // wf_biz_ref.title/summary

    private Long starterId;
    private String starterName;

    private Long assigneeId;
    private String assigneeName;
}
