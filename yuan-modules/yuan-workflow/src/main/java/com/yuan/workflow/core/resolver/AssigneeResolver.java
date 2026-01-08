package com.yuan.workflow.core.resolver;

import com.yuan.system.api.UserQueryApi;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.model.logicflow.LfAssignee;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.model.logicflow.LfProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AssigneeResolver {

    private final UserQueryApi userQueryApi;

    public List<Long> resolve(LfNode node, WfInstance instance) {
//        Map<String, Object> properties = node.getProperties();
//        Assignee assignee = (Assignee) properties.get("assignee");
        LfProperties properties = node.getProperties();

        LfAssignee assignee = properties.getAssignee();
        return assignee.getUserIds();
    }
}
