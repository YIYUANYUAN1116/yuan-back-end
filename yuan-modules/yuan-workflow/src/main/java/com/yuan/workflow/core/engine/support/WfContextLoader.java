package com.yuan.workflow.core.engine.support;

import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.mapper.WfBizRefMapper;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.mapper.WfTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class WfContextLoader {
  private final WfTaskMapper taskMapper;
  private final WfNodeInstanceMapper nodeInstanceMapper;
  private final WfInstanceMapper instanceMapper;
  private final WfDefinitionMapper definitionMapper;
  private final WfBizRefMapper bizRefMapper;

  public TaskCtx loadTaskCtx(Long taskId) {
    WfTask task = taskMapper.selectById(taskId);
    Assert.notNull(task, "任务不存在");

    WfNodeInstance node = nodeInstanceMapper.selectById(task.getNodeInstanceId());
    Assert.notNull(node, "节点不存在");

    WfInstance ins = instanceMapper.selectById(task.getInstanceId());
    Assert.notNull(ins, "实例不存在");

    WfDefinition def = definitionMapper.selectById(ins.getDefinitionId());
    Assert.notNull(def, "流程定义不存在");

    WfBizRef ref = bizRefMapper.selectByInstanceId(ins.getId());
    // ref 可以允许为空：有的流程不绑业务

    return new TaskCtx(task, node, ins, def, ref);
  }

  public record TaskCtx(WfTask task, WfNodeInstance node, WfInstance instance, WfDefinition def, WfBizRef bizRef) {}
}
