package com.yuan.workflow.component;

import com.yuan.common.core.domain.model.LoginUser;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.api.cmd.WorkflowCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WfCmdFiller {
    public void fill(WorkflowCmd cmd) {
        LoginUser u = LoginHelper.getLoginUser();
        cmd.setTenantId(u.getTenantId());
        cmd.setOperatorUserId(u.getUserId());
        cmd.setOperatorUserName(u.getNickName());
        if (cmd instanceof StartProcessCmd startProcessCmd) {
            if (startProcessCmd.getStarterUserId() == null) {
                startProcessCmd.setStarterUserId(u.getUserId());
                startProcessCmd.setStarterUserName(u.getNickName());
                startProcessCmd.setStarterDeptId(u.getDeptId());
                startProcessCmd.setStarterDeptName(u.getDeptName());
            }
        }
    }
}