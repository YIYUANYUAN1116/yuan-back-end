package com.yuan.common.idempotent.config;


import com.yuan.common.idempotent.aspectj.RepeatSubmitAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 幂等功能配置
 *
 * @author Lion Li
 */
@Configuration
public class IdempotentConfig {

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect() {
        return new RepeatSubmitAspect();
    }

}
