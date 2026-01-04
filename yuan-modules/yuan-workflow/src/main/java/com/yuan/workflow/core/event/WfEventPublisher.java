package com.yuan.workflow.core.event;

import com.yuan.workflow.api.event.WfEvent;

public interface WfEventPublisher {
    void publish(WfEvent event);

    void publishAfterCommit(WfEvent event);
}
