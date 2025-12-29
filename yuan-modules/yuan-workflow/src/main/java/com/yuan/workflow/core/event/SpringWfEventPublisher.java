package com.yuan.workflow.core.event;

import com.yuan.workflow.api.event.WfEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringWfEventPublisher implements WfEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(WfEvent event) {
        publisher.publishEvent(event);
    }
}
