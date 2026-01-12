package com.yuan.workflow.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssigneeType {
    POST("POST"),
    ROLE("ROLE"),
    DEPT("DEPT"),
    USER("USER");
    private final String value;
}
