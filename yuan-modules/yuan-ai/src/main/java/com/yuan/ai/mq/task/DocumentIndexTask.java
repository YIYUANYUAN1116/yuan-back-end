package com.yuan.ai.mq.task;

import lombok.Data;

import java.io.Serializable;

/**
 * Parses a source document and prepares chunks for embedding.
 */
@Data
public class DocumentIndexTask implements Serializable {

    private Long docId;

    private Long kbId;

    private String tenantId;

    private String fileName;

    private String contentType;

    private String objectKey;
}
