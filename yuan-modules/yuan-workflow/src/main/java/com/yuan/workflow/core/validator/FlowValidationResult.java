package com.yuan.workflow.core.validator;

import lombok.Value;

import java.util.List;

@Value
public class FlowValidationResult {
    boolean ok;
    List<String> errors;

    public static FlowValidationResult ok() {
        return new FlowValidationResult(true, List.of());
    }

    public static FlowValidationResult fail(List<String> errors) {
        return new FlowValidationResult(false, List.copyOf(errors));
    }
}