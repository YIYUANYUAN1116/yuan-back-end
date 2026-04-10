package com.yuan.workflow.core.engine.runtime;

import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.mapper.WfBizRefMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class BizRefManager {
    private final WfBizRefMapper wfBizRefMapper;

    public void bindWfBizRef(StartCmd cmd, Long instanceId) {
        WfBizRef ref = new WfBizRef();
        ref.setBizType(cmd.getBizType());
        ref.setBizId(cmd.getBizId());
        ref.setBizNo(cmd.getBizNo());
        ref.setInstanceId(instanceId);
        ref.setStatus(InstanceStatus.RUNNING);
        ref.setCreateBy(cmd.getStarterId());
        ref.setRef_type("PRIMARY");
        ref.setCreateTime(new Date());
        ref.setUpdateTime(new Date());
        wfBizRefMapper.insert(ref);
    }
}
