package com.yuan.workflow.core.resolver;

import com.yuan.system.api.UserQueryApi;
import com.yuan.workflow.model.enums.AssigneeKind;
import com.yuan.workflow.model.enums.AssigneeType;
import com.yuan.workflow.model.logicflow.LfAssignee;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.model.logicflow.LfProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AssigneeResolver {

    private final UserQueryApi userQueryApi;

    public Set<Long> resolve(LfNode node) {
        LfProperties properties = node.getProperties();
        LfAssignee assignee = properties.getAssignee();
        AssigneeKind kind = assignee.getKind();
        AssigneeType type = assignee.getType();
        if (AssigneeKind.FIXED.equals(kind)) {
            if (AssigneeType.USER.equals(type)) {
                return assignee.getUserIds();
            }else if (AssigneeType.ROLE.equals(type)) {
                return userQueryApi.findUserIdsByRoleIds(assignee.getRoleIds());
            }else if (AssigneeType.DEPT.equals(type)) {
                return userQueryApi.findUserIdsByDeptIds(assignee.getDeptIds());
            }else if (AssigneeType.POST.equals(type)) {
                return userQueryApi.findUserIdsByPostIds(assignee.getPostIds());
            }
        }else {
            //todo
        }
        return assignee.getUserIds();
    }
}
