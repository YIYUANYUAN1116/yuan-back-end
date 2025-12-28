package com.yuan.workflow.model;

import lombok.Data;

import java.util.List;

@Data
public class FlowDefinitionJson {
    private List<FlowNode> nodes;
    private List<FlowEdge> edges;
}