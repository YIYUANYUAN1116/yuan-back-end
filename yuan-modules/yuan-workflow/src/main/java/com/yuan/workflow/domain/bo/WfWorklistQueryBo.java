package com.yuan.workflow.domain.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WfWorklistQueryBo {
    private String bizType;
    private String bizNo;
    private String instanceStatus;
    private String taskStatus;
    private LocalDateTime instanceStartTime;
    private LocalDateTime instanceEndTime;
    private String starterName;
    private String taskAction;
    private String endReason;
}
