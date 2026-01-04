package com.yuan.workflow.model.logicflow;

import lombok.Data;

import java.util.List;

@Data
public class LfGraph {
  private List<LfNode> nodes;
  private List<LfEdge> edges;
}