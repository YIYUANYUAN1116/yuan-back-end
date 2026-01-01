package com.yuan.workflow.domain.bo;

import lombok.Data;

@Data
public class WfDefinitionDto {
    private Long id;
    private String formSchema;
    private String flowJson;
}
