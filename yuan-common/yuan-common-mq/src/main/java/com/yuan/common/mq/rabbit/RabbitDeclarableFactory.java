package com.yuan.common.mq.rabbit;

import com.yuan.common.mq.properties.MqProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Creates RabbitMQ declarables from common properties.
 */
public final class RabbitDeclarableFactory {

    private RabbitDeclarableFactory() {
    }

    public static List<Declarable> create(MqProperties.Rabbit properties) {
        if (properties == null) {
            return Collections.emptyList();
        }

        List<Declarable> declarables = new ArrayList<>();
        for (MqProperties.Exchange exchange : safeList(properties.getExchanges())) {
            if (!StringUtils.hasText(exchange.getName())) {
                continue;
            }
            declarables.add(createExchange(exchange));
        }
        for (MqProperties.Queue queue : safeList(properties.getQueues())) {
            if (!StringUtils.hasText(queue.getName())) {
                continue;
            }
            declarables.add(createQueue(queue));
        }
        for (MqProperties.Binding binding : safeList(properties.getBindings())) {
            if (!StringUtils.hasText(binding.getQueue()) || !StringUtils.hasText(binding.getExchange())) {
                continue;
            }
            declarables.add(new Binding(
                binding.getQueue(),
                Binding.DestinationType.QUEUE,
                binding.getExchange(),
                defaultString(binding.getRoutingKey()),
                emptyToNull(binding.getArguments())
            ));
        }
        return declarables;
    }

    private static Declarable createExchange(MqProperties.Exchange exchange) {
        Map<String, Object> arguments = emptyToNull(exchange.getArguments());
        return switch (exchange.getType()) {
            case DIRECT -> new DirectExchange(exchange.getName(), exchange.isDurable(), exchange.isAutoDelete(), arguments);
            case FANOUT -> new FanoutExchange(exchange.getName(), exchange.isDurable(), exchange.isAutoDelete(), arguments);
            case TOPIC -> new TopicExchange(exchange.getName(), exchange.isDurable(), exchange.isAutoDelete(), arguments);
        };
    }

    private static org.springframework.amqp.core.Queue createQueue(MqProperties.Queue queue) {
        QueueBuilder builder = queue.isDurable()
            ? QueueBuilder.durable(queue.getName())
            : QueueBuilder.nonDurable(queue.getName());

        if (queue.isExclusive()) {
            builder.exclusive();
        }
        if (queue.isAutoDelete()) {
            builder.autoDelete();
        }
        if (StringUtils.hasText(queue.getDeadLetterExchange())) {
            builder.deadLetterExchange(queue.getDeadLetterExchange());
        }
        if (StringUtils.hasText(queue.getDeadLetterRoutingKey())) {
            builder.deadLetterRoutingKey(queue.getDeadLetterRoutingKey());
        }
        if (!CollectionUtils.isEmpty(queue.getArguments())) {
            builder.withArguments(queue.getArguments());
        }
        return builder.build();
    }

    private static <T> List<T> safeList(List<T> source) {
        return source == null ? Collections.emptyList() : source;
    }

    private static Map<String, Object> emptyToNull(Map<String, Object> source) {
        return CollectionUtils.isEmpty(source) ? null : source;
    }

    private static String defaultString(String value) {
        return value == null ? "" : value;
    }
}
