package com.yuan.workflow.model;

import com.yuan.workflow.domain.enums.AssigneeType;
import lombok.Data;

@Data
public class Assignee {

    /** USER / ROLE / DEPT / STARTER / STARTER_MANAGER */
    private AssigneeType type;

    /** 对应的值（userId / roleKey / deptId） */
    private String value;
}
