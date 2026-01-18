package com.yuan.workflow.component;

import com.yuan.common.core.domain.model.LoginUser;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.workflow.cmd.StartProcessCmd;
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
        if (cmd instanceof StartProcessCmd startProcessCmd) {
            if (startProcessCmd.getStarterId() == null) {
                startProcessCmd.setStarterId(u.getUserId());
                startProcessCmd.setStarterName(u.getNickName());
                startProcessCmd.setStarterDeptId(u.getDeptId());
                startProcessCmd.setStarterDeptName(u.getDeptName());
            }
        }
    }
}