package com.yuan.common.oss.core.domin;

import lombok.Data;

@Data
public class PutObjectReq {
    private OssScope scope;
    private String filename;
    private String contentType;
    private Long sizeBytes;
    private byte[] bytes; // 最小示例，实际也可以用 InputStream
}
