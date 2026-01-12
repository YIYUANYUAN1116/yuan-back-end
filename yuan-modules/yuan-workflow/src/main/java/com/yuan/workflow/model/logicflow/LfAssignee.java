package com.yuan.workflow.model.logicflow;

import com.yuan.workflow.model.enums.AssigneeKind;
import com.yuan.workflow.model.enums.AssigneeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LfAssignee {
    private AssigneeKind kind;
    private AssigneeType type;

    /** 固定审批人 */
    private Set<Long> userIds = new HashSet<>();

    /** 固定角色 */
    private Set<Long> roleIds = new HashSet<>();

    /** 固定岗位 */
    private Set<Long> postIds = new HashSet<>();

    /** 固定部门 */
    private Set<Long> deptIds = new HashSet<>();
}
