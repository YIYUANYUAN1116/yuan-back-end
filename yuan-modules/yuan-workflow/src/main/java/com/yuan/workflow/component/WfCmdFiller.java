package com.yuan.workflow.component;

import com.yuan.common.core.domain.model.LoginUser;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.cmd.WorkflowCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WfCmdFiller {
    public void fill(WorkflowCmd cmd) {
        LoginUser u = LoginHelper.getLoginUser();
        cmd.setTenantId(u.getTenantId());
        cmd.setOperatorId(u.getUserId());
        cmd.setOperatorName(u.getNickName());
        if (cmd instanceof StartCmd startCmd) {
            if (startCmd.getStarterId() == null) {
                startCmd.setStarterId(u.getUserId());
                startCmd.setStarterName(u.getNickName());
                startCmd.setStarterDeptId(u.getDeptId());
                startCmd.setStarterDeptName(u.getDeptName());
            }
        }
    }
}