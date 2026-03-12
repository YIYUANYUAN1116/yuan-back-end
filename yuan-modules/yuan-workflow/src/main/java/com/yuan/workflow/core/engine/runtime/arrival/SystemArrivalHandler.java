package com.yuan.workflow.core.engine.runtime.arrival;

import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.domain.enums.NodeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SystemArrivalHandler implements NodeArrivalHandler{

    @Override
    public boolean supports(String nodeType) {
        return NodeType.SYSTEM_TASK.getCode().equals(nodeType);
    }

    @Override
    public void handle(NodeArrivalContext context) {
        log.info("SystemArrivalHandler handle");
    }
}
