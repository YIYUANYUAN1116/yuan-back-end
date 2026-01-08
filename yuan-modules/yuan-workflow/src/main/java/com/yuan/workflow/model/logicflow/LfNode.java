package com.yuan.workflow.model.logicflow;

import lombok.Data;

@Data
public class LfNode {
  //结点ID
  private String id;
  //x坐标
  private String x;
  //y坐标
  private String y;
  //文字
  private LfText text;
  //结点类型
  private String type;

  private LfProperties properties;
  //参数 wfType/assignee
//  private Map<String, Object> properties;

}