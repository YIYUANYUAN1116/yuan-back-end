package com.yuan.common.mq.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Default RabbitMQ publisher confirm callback.
 */
public class DefaultRabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {

    private static final Logger log = LoggerFactory.getLogger(DefaultRabbitConfirmCallback.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String correlationId = correlationData == null ? null : correlationData.getId();
        if (ack) {
            log.debug("RabbitMQ message confirmed, correlationId={}", correlationId);
            return;
        }
        log.warn("RabbitMQ message confirm failed, correlationId={}, cause={}", correlationId, cause);
    }
}
