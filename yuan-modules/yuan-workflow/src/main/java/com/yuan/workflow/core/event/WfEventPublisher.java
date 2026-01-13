package com.yuan.workflow.core.event;



public interface WfEventPublisher {
    void publish(WfEvent event);

    void publishAfterCommit(WfEvent event);
}
