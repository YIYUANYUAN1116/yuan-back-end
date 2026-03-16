package com.yuan.ai.api;

public interface AiStructuredResultParser {

    <T> T parse(String content, Class<T> clazz);

}