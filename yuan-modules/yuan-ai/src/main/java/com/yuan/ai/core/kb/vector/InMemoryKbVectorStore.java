package com.yuan.ai.core.kb.vector;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryKbVectorStore implements KbVectorStore {

    private final ConcurrentHashMap<String, KbVectorItem> vectors = new ConcurrentHashMap<>();

    @Override
    public String storeType() {
        return "MEMORY";
    }

    @Override
    public void upsert(KbVectorItem item) {
        vectors.put(item.getVectorId(), item);
    }

    @Override
    public void deleteByDocument(String tenantId, Long docId) {
        vectors.entrySet().removeIf(entry -> Objects.equals(entry.getValue().getTenantId(), tenantId)
                && Objects.equals(entry.getValue().getDocId(), docId));
    }

    @Override
    public List<KbVectorSearchHit> search(String tenantId, Collection<Long> kbIds, float[] queryVector, int topK, double minScore) {
        return vectors.values().stream()
                .filter(item -> Objects.equals(item.getTenantId(), tenantId))
                .filter(item -> kbIds == null || kbIds.isEmpty() || kbIds.contains(item.getKbId()))
                .map(item -> KbVectorSearchHit.builder()
                        .vectorId(item.getVectorId())
                        .kbId(item.getKbId())
                        .docId(item.getDocId())
                        .chunkId(item.getChunkId())
                        .content(item.getContent())
                        .score(cosine(queryVector, item.getVector()))
                        .build())
                .filter(hit -> hit.getScore() >= minScore)
                .sorted(Comparator.comparingDouble(KbVectorSearchHit::getScore).reversed())
                .limit(Math.max(1, topK))
                .toList();
    }

    private double cosine(float[] a, float[] b) {
        if (a == null || b == null || a.length == 0 || b.length == 0 || a.length != b.length) {
            return 0D;
        }
        double dot = 0D;
        double normA = 0D;
        double normB = 0D;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        if (normA == 0D || normB == 0D) {
            return 0D;
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
