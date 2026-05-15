package com.yuan.common.mq.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Default RabbitMQ returned message callback.
 */
public class DefaultRabbitReturnsCallback implements RabbitTemplate.ReturnsCallback {

    private static final Logger log = LoggerFactory.getLogger(DefaultRabbitReturnsCallback.class);

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.warn(
            "RabbitMQ message returned, exchange={}, routingKey={}, replyCode={}, replyText={}",
            returned.getExchange(),
            returned.getRoutingKey(),
            returned.getReplyCode(),
            returned.getReplyText()
        );
    }
}
