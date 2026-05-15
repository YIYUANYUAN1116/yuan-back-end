package com.yuan.common.mq.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Common MQ send request.
 */
public class MqSendRequest {

    private final String exchange;
    private final String routingKey;
    private final Object payload;
    private final String messageId;
    private final String correlationId;
    private final Integer delayMillis;
    private final Map<String, Object> headers;

    private MqSendRequest(Builder builder) {
        this.exchange = builder.exchange;
        this.routingKey = builder.routingKey;
        this.payload = builder.payload;
        this.messageId = builder.messageId;
        this.correlationId = builder.correlationId;
        this.delayMillis = builder.delayMillis;
        this.headers = Collections.unmodifiableMap(new LinkedHashMap<>(builder.headers));
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getExchange() {
        return exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public Object getPayload() {
        return payload;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public Integer getDelayMillis() {
        return delayMillis;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public static class Builder {

        private String exchange;
        private String routingKey;
        private Object payload;
        private String messageId;
        private String correlationId;
        private Integer delayMillis;
        private final Map<String, Object> headers = new LinkedHashMap<>();

        public Builder exchange(String exchange) {
            this.exchange = exchange;
            return this;
        }

        public Builder routingKey(String routingKey) {
            this.routingKey = routingKey;
            return this;
        }

        public Builder payload(Object payload) {
            this.payload = payload;
            return this;
        }

        public Builder messageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public Builder delayMillis(Integer delayMillis) {
            this.delayMillis = delayMillis;
            return this;
        }

        public Builder header(String name, Object value) {
            if (name != null && value != null) {
                this.headers.put(name, value);
            }
            return this;
        }

        public Builder headers(Map<String, Object> headers) {
            if (headers != null) {
                headers.forEach(this::header);
            }
            return this;
        }

        public MqSendRequest build() {
            return new MqSendRequest(this);
        }
    }
}
