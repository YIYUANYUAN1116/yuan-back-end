package com.yuan.workflow.domain.vo;

import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.domain.enums.TaskStatus;
import com.yuan.workflow.domain.enums.WfEndReason;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class WorkItemRowVO {
    private Long taskId;          // 待办/已办才有
    private Long instanceId;
    private Long nodeInstanceId;

    private TaskStatus taskStatus;    // TODO/DONE/CANCELED
    private TaskAction taskAction;    // APPROVE/REJECT/ROLLBACK/WITHDRAW/TRANSFER
    private String taskComment;
    private Date taskCreateTime;
    private LocalDateTime taskFinishTime;

    private InstanceStatus instanceStatus;   // RUNNING/APPROVED/REJECTED/CANCELED...
    private LocalDateTime instanceStartTime;
    private LocalDateTime instanceEndTime;
    private WfEndReason instanceEndReason;
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
