package com.yuan.ai.mq;

import com.yuan.ai.mq.task.DocumentIndexTask;
import com.yuan.ai.mq.task.EmbeddingTask;
import com.yuan.ai.service.KbPipelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumes knowledge base indexing tasks.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KbIndexTaskListener {

    private final KbPipelineService kbPipelineService;

    @RabbitListener(queues = KbMqConstants.DOCUMENT_INDEX_QUEUE)
    public void handleDocumentIndexTask(DocumentIndexTask task) {
        log.info("[KbIndexTaskListener][handleDocumentIndexTask] receive task docId={}", task.getDocId());
        kbPipelineService.processDocumentIndexTask(task);
    }

    @RabbitListener(queues = KbMqConstants.EMBEDDING_QUEUE)
    public void handleEmbeddingTask(EmbeddingTask task) {
        log.info("[KbIndexTaskListener][handleEmbeddingTask] receive task docId={}", task.getDocId());
        kbPipelineService.processEmbeddingTask(task);
    }
}
