package com.yuan.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ChatAsyncConfig {

    @Bean("chatTaskExecutor")
    public TaskExecutor chatTaskExecutor() {
        ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
        ex.setCorePoolSize(8);
        ex.setMaxPoolSize(32);
        ex.setQueueCapacity(500);
        ex.setThreadNamePrefix("chat-");
        ex.initialize();
        return ex;
    }
}