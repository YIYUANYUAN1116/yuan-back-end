package com.yuan.common.oss.core.domin;

import com.yuan.common.oss.enums.StorageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileObjectKey {
    private StorageType storage;
    private String bucket;
    private String objectKey;

    private String etag;
    private Long sizeBytes;
    private String contentType;
    private String filename;
    private String sha256; // 可选
}
