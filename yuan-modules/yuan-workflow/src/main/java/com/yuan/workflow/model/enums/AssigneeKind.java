package com.yuan.workflow.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssigneeKind {
    FIXED("FIXED"),
    RULE("RULE");
    private final String value;
}
