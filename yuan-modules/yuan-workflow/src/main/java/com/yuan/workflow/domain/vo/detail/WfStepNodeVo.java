package com.yuan.workflow.domain.vo.detail;

import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WfStepNodeVo {
    private Long id;
    private Long instanceId;
    private String nodeKey;
    private String nodeName;
    private NodeType nodeType;
    private Long operatorId;
    private NodeStatus status;
    private Integer orderNo;
    private LocalDateTime createTime;
    private LocalDateTime finishedTime;
}
