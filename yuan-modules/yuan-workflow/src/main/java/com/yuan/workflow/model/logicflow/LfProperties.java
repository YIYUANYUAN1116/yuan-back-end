package com.yuan.workflow.model.logicflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LfProperties {
    private String width;
    private String height;
    private String wfType;
    private LfAssignee assignee;
}
