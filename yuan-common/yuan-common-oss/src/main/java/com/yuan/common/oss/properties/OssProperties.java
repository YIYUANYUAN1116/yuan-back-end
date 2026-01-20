package com.yuan.common.oss.properties;

import com.yuan.common.oss.enums.StorageType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    private StorageType storage = StorageType.S3_COMPATIBLE;
    private String bucket;
    private Integer presignExpireSeconds = 600;

    private Multipart multipart = new Multipart();
    private S3 s3 = new S3();

    @Data
    public static class Multipart {
        private Integer partSizeMb = 16;
    }

    @Data
    public static class S3 {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String region = "us-east-1";
        private Boolean pathStyleAccess = true;
    }
}

