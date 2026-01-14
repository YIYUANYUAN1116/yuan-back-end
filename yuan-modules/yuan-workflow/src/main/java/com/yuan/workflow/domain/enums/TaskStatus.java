package com.yuan.workflow.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yuan.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus implements BaseEnum {

    //| 场景       | Task 状态     | Node 状态  |
    //| -------- | ----------- | -------- |
    //| 节点创建     | TODO        | WAIT     |
    //| 单人审批完成   | DONE        | DONE     |
    //| 会签完成一部分  | DONE / TODO | WAIT     |
    //| 会签完成全部   | DONE        | DONE     |
    //| rollback | CANCELED    | CANCELED |
    //| withdraw | CANCELED    | CANCELED |
    //| 跳转       | CANCELED    | CANCELED |

    TODO("TODO", "待处理"),
    DONE("DONE", "已完成"),
    CANCELED("CANCELED", "已取消");

    @EnumValue
    private final String code;
    private final String desc;
}
