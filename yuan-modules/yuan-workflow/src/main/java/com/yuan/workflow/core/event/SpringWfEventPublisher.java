package com.yuan.workflow.core.event;

import com.yuan.workflow.api.event.WfEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@RequiredArgsConstructor
public class SpringWfEventPublisher implements WfEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(WfEvent event) {
        publisher.publishEvent(event);
    }

    @Override
    public void publishAfterCommit(WfEvent event) {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        publisher.publishEvent(event);
                    }
                }
        );
    }
}
