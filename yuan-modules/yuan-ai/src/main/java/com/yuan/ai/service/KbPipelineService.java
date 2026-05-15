package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbDocumentVo;
import org.springframework.web.multipart.MultipartFile;

public interface KbPipelineService {
    KbDocumentVo uploadAndIndex(Long kbId, MultipartFile file);

    KbDocumentVo parseAndIndex(Long docId, String fileName, String contentType, byte[] bytes);

    KbDocumentVo rebuildDocumentIndex(Long docId);

    int rebuildKnowledgeBaseIndex(Long kbId);

    void deleteDocumentIndex(Long docId);
}
