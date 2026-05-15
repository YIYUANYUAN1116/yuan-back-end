package com.yuan.common.mq.core;

/**
 * Common MQ message header names.
 */
public final class MqMessageHeaders {

    public static final String TRACE_ID = "traceId";
    public static final String TENANT_ID = "tenantId";
    public static final String MESSAGE_ID = "messageId";
    public static final String DELAY = "x-delay";

    private MqMessageHeaders() {
    }
}
