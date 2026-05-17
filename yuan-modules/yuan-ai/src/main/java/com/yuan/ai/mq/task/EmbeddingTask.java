package com.yuan.ai.mq.task;

import lombok.Data;

import java.io.Serializable;

/**
 * Embeds all chunks for a parsed knowledge base document.
 */
@Data
public class EmbeddingTask implements Serializable {

    private Long docId;

    private Long kbId;

    private String tenantId;
}
