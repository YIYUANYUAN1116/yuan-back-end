package com.yuan.common.oss.core.domin;

import lombok.Data;

@Data
public class MultipartSession {
    // 对象存储 uploadId
    private String uploadId;
    private String bucket;
    private String objectKey;

    private Integer partSizeBytes;
    private Integer totalParts;
    private Integer currentPartNumber;

    // common-oss 的 sessionId
    private String sessionId;
}
