package com.yuan.common.mq.config;

import com.yuan.common.mq.core.MqSender;
import com.yuan.common.mq.properties.MqProperties;
import com.yuan.common.mq.rabbit.DefaultRabbitConfirmCallback;
import com.yuan.common.mq.rabbit.DefaultRabbitReturnsCallback;
import com.yuan.common.mq.rabbit.RabbitDeclarableFactory;
import com.yuan.common.mq.rabbit.RabbitMqSender;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * RabbitMQ base configuration.
 */
@AutoConfiguration(after = RabbitAutoConfiguration.class)
@ConditionalOnClass(RabbitTemplate.class)
@EnableConfigurationProperties(MqProperties.class)
@ConditionalOnProperty(prefix = "yuan.mq", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RabbitMqConfig {

    @Bean
    @ConditionalOnMissingBean(name = "mqMessageConverter")
    public MessageConverter mqMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplateCustomizer mqRabbitTemplateCustomizer(MessageConverter mqMessageConverter,
                                                               MqProperties mqProperties,
                                                               ObjectProvider<RabbitTemplate.ConfirmCallback> confirmCallback,
                                                               ObjectProvider<RabbitTemplate.ReturnsCallback> returnsCallback) {
        return rabbitTemplate -> {
            rabbitTemplate.setMessageConverter(mqMessageConverter);
            rabbitTemplate.setMandatory(mqProperties.getRabbit().isMandatory());
            if (mqProperties.getRabbit().isConfirmCallbackEnabled()) {
                confirmCallback.ifAvailable(rabbitTemplate::setConfirmCallback);
            }
            if (mqProperties.getRabbit().isReturnsCallbackEnabled()) {
                returnsCallback.ifAvailable(rabbitTemplate::setReturnsCallback);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
        prefix = "yuan.mq.rabbit",
        name = "confirm-callback-enabled",
        havingValue = "true",
        matchIfMissing = true
    )
    public RabbitTemplate.ConfirmCallback rabbitConfirmCallback() {
        return new DefaultRabbitConfirmCallback();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
        prefix = "yuan.mq.rabbit",
        name = "returns-callback-enabled",
        havingValue = "true",
        matchIfMissing = true
    )
    public RabbitTemplate.ReturnsCallback rabbitReturnsCallback() {
        return new DefaultRabbitReturnsCallback();
    }

    @Bean
    public BeanPostProcessor mqCachingConnectionFactoryPostProcessor(MqProperties mqProperties) {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof CachingConnectionFactory cachingConnectionFactory) {
                    if (mqProperties.getRabbit().isConfirmCallbackEnabled()) {
                        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
                    }
                    if (mqProperties.getRabbit().isReturnsCallbackEnabled()) {
                        cachingConnectionFactory.setPublisherReturns(true);
                    }
                }
                return bean;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "yuan.mq", name = "type", havingValue = "rabbit", matchIfMissing = true)
    public MqSender mqSender(RabbitTemplate rabbitTemplate, MqProperties mqProperties) {
        return new RabbitMqSender(rabbitTemplate, mqProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "yuan.mq.rabbit", name = "auto-declare", havingValue = "true", matchIfMissing = true)
    public Declarables mqRabbitDeclarables(MqProperties mqProperties) {
        return new Declarables(RabbitDeclarableFactory.create(mqProperties.getRabbit()));
    }
}
