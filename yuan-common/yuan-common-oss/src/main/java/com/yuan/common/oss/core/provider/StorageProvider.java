package com.yuan.common.oss.core.provider;

import com.yuan.common.oss.enums.StorageType;

import java.util.List;

public interface StorageProvider {

    StorageType type();

    PutResult putObject(String bucket, String objectKey, String contentType, byte[] bytes);

    MultipartInitResult initMultipart(String bucket, String objectKey, String contentType);

    String presignUploadPartUrl(String bucket, String objectKey, String uploadId, int partNumber, int expireSeconds);

    CompleteResult completeMultipart(String bucket, String objectKey, String uploadId, List<CompletedPart> parts);

    void abortMultipart(String bucket, String objectKey, String uploadId);

    String presignGetUrl(String bucket, String objectKey, int expireSeconds);

    void deleteObject(String bucket, String objectKey);

    CopyResult copyObject(String srcBucket, String srcKey, String destBucket, String destKey);

    record CopyResult(String etag) {}

    record PutResult(String etag) {}
    record MultipartInitResult(String uploadId) {}
    record CompleteResult(String etag) {}
    record CompletedPart(int partNumber, String etag) {}
}
