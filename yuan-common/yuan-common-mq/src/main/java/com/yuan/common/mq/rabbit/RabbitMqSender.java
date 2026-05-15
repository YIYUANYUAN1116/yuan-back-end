package com.yuan.common.mq.rabbit;

import com.yuan.common.mq.core.MqMessageHeaders;
import com.yuan.common.mq.core.MqSendException;
import com.yuan.common.mq.core.MqSendRequest;
import com.yuan.common.mq.core.MqSender;
import com.yuan.common.mq.properties.MqProperties;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * RabbitMQ sender implementation.
 */
public class RabbitMqSender implements MqSender {

    private final RabbitTemplate rabbitTemplate;
    private final MqProperties mqProperties;

    public RabbitMqSender(RabbitTemplate rabbitTemplate, MqProperties mqProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.mqProperties = mqProperties;
    }

    @Override
    public void send(MqSendRequest request) {
        if (request == null || request.getPayload() == null) {
            throw new IllegalArgumentException("MQ send payload must not be null");
        }

        String exchange = resolveExchange(request.getExchange());
        String routingKey = request.getRoutingKey() == null ? "" : request.getRoutingKey();
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, request.getPayload(), message -> {
                if (StringUtils.hasText(request.getMessageId())) {
                    message.getMessageProperties().setMessageId(request.getMessageId());
                    message.getMessageProperties().setHeader(MqMessageHeaders.MESSAGE_ID, request.getMessageId());
                }
                if (StringUtils.hasText(request.getCorrelationId())) {
                    message.getMessageProperties().setCorrelationId(request.getCorrelationId());
                }
                if (request.getDelayMillis() != null && request.getDelayMillis() > 0) {
                    message.getMessageProperties().setHeader(MqMessageHeaders.DELAY, request.getDelayMillis());
                }
                for (Map.Entry<String, Object> entry : request.getHeaders().entrySet()) {
                    message.getMessageProperties().setHeader(entry.getKey(), entry.getValue());
                }
                return message;
            });
        } catch (AmqpException ex) {
            throw new MqSendException("MQ message send failed", ex);
        }
    }

    private String resolveExchange(String requestExchange) {
        if (StringUtils.hasText(requestExchange)) {
            return requestExchange;
        }
        return mqProperties.getRabbit().getDefaultExchange() == null
            ? ""
            : mqProperties.getRabbit().getDefaultExchange();
    }
}
