package com.yuan.common.oss.core.domin;

import lombok.Data;

import java.util.List;

@Data
public class CompleteMultipartReq {
    private String sessionId;
    private String uploadId;
    private String bucket;
    private String objectKey;
    private List<PartEtag> parts;
    private String filename;
    private String contentType;
    private Long sizeBytes;

    @Data
    public static class PartEtag {
        private Integer partNumber;
        private String etag;
    }
}
