package com.yuan.common.mq.properties;

import com.yuan.common.mq.enums.MqType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common MQ properties.
 */
@ConfigurationProperties(prefix = "yuan.mq")
public class MqProperties {

    private boolean enabled = true;
    private MqType type = MqType.RABBIT;
    private Rabbit rabbit = new Rabbit();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public MqType getType() {
        return type;
    }

    public void setType(MqType type) {
        this.type = type;
    }

    public Rabbit getRabbit() {
        return rabbit;
    }

    public void setRabbit(Rabbit rabbit) {
        this.rabbit = rabbit;
    }

    public static class Rabbit {

        private boolean autoDeclare = true;
        private boolean mandatory = true;
        private boolean confirmCallbackEnabled = true;
        private boolean returnsCallbackEnabled = true;
        private String defaultExchange = "";
        private List<Exchange> exchanges = new ArrayList<>();
        private List<Queue> queues = new ArrayList<>();
        private List<Binding> bindings = new ArrayList<>();

        public boolean isAutoDeclare() {
            return autoDeclare;
        }

        public void setAutoDeclare(boolean autoDeclare) {
            this.autoDeclare = autoDeclare;
        }

        public boolean isMandatory() {
            return mandatory;
        }

        public void setMandatory(boolean mandatory) {
            this.mandatory = mandatory;
        }

        public boolean isConfirmCallbackEnabled() {
            return confirmCallbackEnabled;
        }

        public void setConfirmCallbackEnabled(boolean confirmCallbackEnabled) {
            this.confirmCallbackEnabled = confirmCallbackEnabled;
        }

        public boolean isReturnsCallbackEnabled() {
            return returnsCallbackEnabled;
        }

        public void setReturnsCallbackEnabled(boolean returnsCallbackEnabled) {
            this.returnsCallbackEnabled = returnsCallbackEnabled;
        }

        public String getDefaultExchange() {
            return defaultExchange;
        }

        public void setDefaultExchange(String defaultExchange) {
            this.defaultExchange = defaultExchange;
        }

        public List<Exchange> getExchanges() {
            return exchanges;
        }

        public void setExchanges(List<Exchange> exchanges) {
            this.exchanges = exchanges;
        }

        public List<Queue> getQueues() {
            return queues;
        }

        public void setQueues(List<Queue> queues) {
            this.queues = queues;
        }

        public List<Binding> getBindings() {
            return bindings;
        }

        public void setBindings(List<Binding> bindings) {
            this.bindings = bindings;
        }
    }

    public static class Exchange {

        private String name;
        private ExchangeType type = ExchangeType.TOPIC;
        private boolean durable = true;
        private boolean autoDelete = false;
        private Map<String, Object> arguments = new HashMap<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ExchangeType getType() {
            return type;
        }

        public void setType(ExchangeType type) {
            this.type = type;
        }

        public boolean isDurable() {
            return durable;
        }

        public void setDurable(boolean durable) {
            this.durable = durable;
        }

        public boolean isAutoDelete() {
            return autoDelete;
        }

        public void setAutoDelete(boolean autoDelete) {
            this.autoDelete = autoDelete;
        }

        public Map<String, Object> getArguments() {
            return arguments;
        }

        public void setArguments(Map<String, Object> arguments) {
            this.arguments = arguments;
        }
    }

    public static class Queue {

        private String name;
        private boolean durable = true;
        private boolean exclusive = false;
        private boolean autoDelete = false;
        private String deadLetterExchange;
        private String deadLetterRoutingKey;
        private Map<String, Object> arguments = new HashMap<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDurable() {
            return durable;
        }

        public void setDurable(boolean durable) {
            this.durable = durable;
        }

        public boolean isExclusive() {
            return exclusive;
        }

        public void setExclusive(boolean exclusive) {
            this.exclusive = exclusive;
        }

        public boolean isAutoDelete() {
            return autoDelete;
        }

        public void setAutoDelete(boolean autoDelete) {
            this.autoDelete = autoDelete;
        }

        public String getDeadLetterExchange() {
            return deadLetterExchange;
        }

        public void setDeadLetterExchange(String deadLetterExchange) {
            this.deadLetterExchange = deadLetterExchange;
        }

        public String getDeadLetterRoutingKey() {
            return deadLetterRoutingKey;
        }

        public void setDeadLetterRoutingKey(String deadLetterRoutingKey) {
            this.deadLetterRoutingKey = deadLetterRoutingKey;
        }

        public Map<String, Object> getArguments() {
            return arguments;
        }

        public void setArguments(Map<String, Object> arguments) {
            this.arguments = arguments;
        }
    }

    public static class Binding {

        private String queue;
        private String exchange;
        private String routingKey = "";
        private Map<String, Object> arguments = new HashMap<>();

        public String getQueue() {
            return queue;
        }

        public void setQueue(String queue) {
            this.queue = queue;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public Map<String, Object> getArguments() {
            return arguments;
        }

        public void setArguments(Map<String, Object> arguments) {
            this.arguments = arguments;
        }
    }

    public enum ExchangeType {

        DIRECT,
        TOPIC,
        FANOUT
    }
}
