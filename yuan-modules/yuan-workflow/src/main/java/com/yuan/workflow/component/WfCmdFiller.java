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
        cmd.setOperatorId(u.getUserId());
        cmd.setOperatorName(u.getNickName());
        if (cmd instanceof StartProcessCmd startProcessCmd) {
            if (startProcessCmd.getStartId() == null) {
                startProcessCmd.setStartId(u.getUserId());
                startProcessCmd.setStartName(u.getNickName());
                startProcessCmd.setStartDeptId(u.getDeptId());
                startProcessCmd.setStartDeptName(u.getDeptName());
            }
        }
    }
}