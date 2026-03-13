package com.yuan.workflow.model.logicflow;

import com.yuan.workflow.core.engine.runtime.arrival.AiNodeConfig;
import com.yuan.workflow.model.Expression;
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
    private Expression condition;
    private AiNodeConfig aiNodeConfig;
}
