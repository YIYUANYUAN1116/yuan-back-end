package com.yuan.ai.mq;

import com.yuan.ai.mq.task.DocumentIndexTask;
import com.yuan.ai.mq.task.EmbeddingTask;
import com.yuan.common.mq.core.MqSendRequest;
import com.yuan.common.mq.core.MqSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Publishes knowledge base indexing tasks after the current transaction commits.
 */
@Component
@RequiredArgsConstructor
public class KbIndexTaskProducer {

    private final MqSender mqSender;

    public void sendDocumentIndexTask(DocumentIndexTask task) {
        sendAfterCommit(MqSendRequest.builder()
                .exchange(KbMqConstants.EXCHANGE)
                .routingKey(KbMqConstants.DOCUMENT_INDEX_ROUTING_KEY)
                .payload(task)
                .messageId("kb-doc-index-" + task.getDocId())
                .correlationId(String.valueOf(task.getDocId()))
                .build());
    }

    public void sendEmbeddingTask(EmbeddingTask task) {
        sendAfterCommit(MqSendRequest.builder()
                .exchange(KbMqConstants.EXCHANGE)
                .routingKey(KbMqConstants.EMBEDDING_ROUTING_KEY)
                .payload(task)
                .messageId("kb-embedding-" + task.getDocId())
                .correlationId(String.valueOf(task.getDocId()))
                .build());
    }

    private void sendAfterCommit(MqSendRequest request) {
        //判断当前线程是否处于一个激活的事务同步上下文中
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            //没事务，直接发送
            mqSender.send(request);
            return;
        }
        //向当前线程绑定的事务同步器中注册一个 TransactionSynchronization 回调对象。
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //有事务，事务提交后发送。
                mqSender.send(request);
            }
        });
    }
}
