package com.yuan.ai.core.kb.vector;

import java.util.Collection;
import java.util.List;

public interface KbVectorStore {
    String storeType();

    void upsert(KbVectorItem item);

    void deleteByDocument(String tenantId, Long docId);

    List<KbVectorSearchHit> search(String tenantId, Collection<Long> kbIds, float[] queryVector, int topK, double minScore);
}
