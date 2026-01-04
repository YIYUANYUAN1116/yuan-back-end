package com.yuan.workflow.core.resolver;

import com.yuan.system.api.UserQueryApi;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.model.Assignee;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AssigneeResolver {

    private final UserQueryApi userQueryApi;

    public List<Long> resolve(LfNode node, WfInstance instance) {
        Map<String, Object> properties = node.getProperties();
        Assignee assignee = (Assignee) properties.get("assignee");
        return switch (assignee.getType()) {
            case ROLE -> userQueryApi.findUserIdsByRole(
                    assignee.getValue(), instance.getTenantId());
            case DEPT -> userQueryApi.findUserIdsByDept(
                    Long.valueOf(assignee.getValue()), instance.getTenantId());
            case STARTER -> List.of(instance.getStartUserId());
            case STARTER_MANAGER -> List.of(
                    userQueryApi.findLeaderUserId(instance.getStartUserId()));
            case USER -> List.of(Long.valueOf(assignee.getValue()));
        };
    }
}
