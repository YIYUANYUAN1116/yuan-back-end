package com.yuan.workflow.domain.vo;

import lombok.Data;

@Data
public class RollbackTargetVO {
    private String nodeKey;
    private String nodeName;
    private Integer orderNo;
}