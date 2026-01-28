package com.yuan.workflow.core.engine.handler;


import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.core.engine.support.InstanceStateManager;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import com.yuan.workflow.mapper.WfInstanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 撤回申请
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawHandler implements  CommandHandler<WithdrawCmd,Void>{
    private final WfInstanceMapper wfInstanceMapper;
    private final WfOperationGuard wfOperationGuard;
    private final InstanceStateManager instanceStateManager;

    @Override
    @Transactional
    public Void handle(WithdrawCmd cmd) {
        Long instanceId = cmd.getInstanceId();
        WfInstance wfInstance = wfInstanceMapper.selectById(instanceId);
        //校验
        wfOperationGuard.assertWithDraw(wfInstance,cmd.getOperatorId());

        instanceStateManager.finishWithDraw(wfInstance,cmd);

        return null;
    }
}
