package com.yuan.workflow.model.logicflow;

import lombok.Data;

import java.util.Map;

@Data
public class LfEdge {
  //边Id
  private String id;
  //边类型
  private String type;
  //开始结点id
  private String sourceNodeId;
  //结束节点 Id
  private String targetNodeId;
  //文字
  private LfText text;
  //参数
  private Map<String, Object> properties; // condition.expression
}