package com.yuan.common.mq.core;

/**
 * MQ send exception.
 */
public class MqSendException extends RuntimeException {

    public MqSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
