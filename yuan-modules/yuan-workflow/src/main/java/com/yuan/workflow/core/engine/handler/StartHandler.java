package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.core.engine.runtime.BizRefManager;
import com.yuan.workflow.core.engine.runtime.InstanceStateManager;
import com.yuan.workflow.core.engine.runtime.ProcessAdvancer;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartHandler implements  CommandHandler<StartCmd,Long>{
    private final WfDefinitionMapper definitionMapper;
    private final InstanceStateManager instanceStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final ProcessAdvancer processAdvancer;
    private final BizRefManager bizRefManager;


    @Override
    @Transactional
    public Long handle(StartCmd cmd) {
        String tenantId = cmd.getTenantId();

        // 1) 查最新已发布定义
        WfDefinition def = definitionMapper.selectLatestPublished(tenantId, cmd.getDefinitionKey());
        wfOperationGuard.assertStartInstance(def,cmd);

        // 2) 创建实例
        WfInstance instance =  instanceStateManager.createInstance(cmd, def);

        // 3) 绑定业务
        bizRefManager.bindWfBizRef(cmd,instance.getId());

        processAdvancer.start(def,instance,cmd);

        return instance.getId();
    }
}
