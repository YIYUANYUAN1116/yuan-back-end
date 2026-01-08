package com.yuan.workflow.model.logicflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LfText {
    //x坐标
    private String x;
    //y坐标
    private String y;

    private String value;
}
