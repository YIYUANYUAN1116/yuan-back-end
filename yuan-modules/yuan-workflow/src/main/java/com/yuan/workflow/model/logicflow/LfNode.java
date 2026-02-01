package com.yuan.workflow.model.logicflow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LfNode {
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
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

}