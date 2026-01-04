package com.yuan.workflow.core.engine.handler;

public interface CommandHandler<C, R> {
    R handle(C cmd);
}
