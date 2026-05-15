package com.yuan.common.mq.core;

/**
 * Common MQ sender abstraction.
 */
public interface MqSender {

    void send(MqSendRequest request);

    default void send(String exchange, String routingKey, Object payload) {
        send(MqSendRequest.builder()
            .exchange(exchange)
            .routingKey(routingKey)
            .payload(payload)
            .build());
    }
}
