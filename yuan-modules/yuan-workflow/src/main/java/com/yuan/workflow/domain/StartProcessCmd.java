package com.yuan.workflow.domain;

import lombok.Data;

import java.util.Map;

@Data
public class StartProcessCmd {
    private String processKey;
    private String businessKey;
    private Map<String, Object> variables;
}