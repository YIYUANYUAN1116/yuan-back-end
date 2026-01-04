package com.yuan.workflow.model.logicflow;

import com.yuan.workflow.api.enums.NodeType;
import lombok.Data;

import java.util.Map;

@Data
public class LfNode {
  //结点ID
  private String id;
  //结点类型
  private NodeType type;
  //x坐标
  private String x;
  //y坐标
  private String y;
  //文字
  private String text;
  //参数 wfType/assignee
  private Map<String, Object> properties;
}