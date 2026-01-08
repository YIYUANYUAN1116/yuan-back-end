package com.yuan.workflow.model.logicflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LfAssignee {
    private String kind;
    private Object users;
    private List<Long> userIds;
}
