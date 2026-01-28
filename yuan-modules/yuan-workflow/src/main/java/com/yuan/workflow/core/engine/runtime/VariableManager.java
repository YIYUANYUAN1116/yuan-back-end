package com.yuan.workflow.core.engine.runtime;

import com.yuan.common.json.utils.JsonUtils;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.mapper.WfInstanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class VariableManager {
  private final WfInstanceMapper instanceMapper;

  public Map<String, Object> getVars(WfInstance ins) {
    return JsonUtils.parseMap(ins.getVariables());
  }

  public void mergeAndSave(WfInstance ins, Map<String, Object> patch) {
    if (patch == null || patch.isEmpty()) return;
    Map<String, Object> vars = JsonUtils.parseMap(ins.getVariables());
    if (vars == null) return;
    vars.putAll(patch);
    ins.setVariables(JsonUtils.toJsonString(vars));
    instanceMapper.updateById(ins);
  }
}
