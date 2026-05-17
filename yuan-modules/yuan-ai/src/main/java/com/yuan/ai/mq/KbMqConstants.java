package com.yuan.ai.mq;

/**
 * Knowledge base MQ topology.
 */
public final class KbMqConstants {

    public static final String EXCHANGE = "yuan.ai.kb.exchange";

    public static final String DOCUMENT_INDEX_QUEUE = "yuan.ai.kb.document.index.queue";
    public static final String DOCUMENT_INDEX_ROUTING_KEY = "yuan.ai.kb.document.index";

    public static final String EMBEDDING_QUEUE = "yuan.ai.kb.embedding.queue";
    public static final String EMBEDDING_ROUTING_KEY = "yuan.ai.kb.embedding";

    private KbMqConstants() {
    }
}
