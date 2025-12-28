package com.yuan.workflow.model;

import com.yuan.workflow.enums.ConditionOperator;
import lombok.Data;

@Data
public class Expression {

    /** 变量名，如 amount / leaveType */
    private String field;

    /** 操作符：EQ / GT / LT / IN ... */
    private ConditionOperator operator;

    /** 比较值（运行时解析） */
    private Object value;
}
