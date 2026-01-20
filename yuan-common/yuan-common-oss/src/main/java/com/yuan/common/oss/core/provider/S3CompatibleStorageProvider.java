package com.yuan.common.oss.core.provider;

import com.yuan.common.oss.enums.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class S3CompatibleStorageProvider implements StorageProvider {

    private final S3Client s3;
    private final S3Presigner preSigner;

    @Override
    public StorageType type() { return StorageType.S3_COMPATIBLE; }

    @Override
    public PutResult putObject(String bucket, String objectKey, String contentType, byte[] bytes) {
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .build();

        PutObjectResponse resp = s3.putObject(req, RequestBody.fromBytes(bytes));
        return new PutResult(resp.eTag());
    }

    @Override
    public MultipartInitResult initMultipart(String bucket, String objectKey, String contentType) {
        CreateMultipartUploadResponse resp = s3.createMultipartUpload(b -> b
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType));

        return new MultipartInitResult(resp.uploadId());
    }

    @Override
    public String presignUploadPartUrl(String bucket, String objectKey, String uploadId, int partNumber, int expireSeconds) {
        UploadPartRequest upr = UploadPartRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .uploadId(uploadId)
                .partNumber(partNumber)
                .build();

        PresignedUploadPartRequest pre = preSigner.presignUploadPart(b -> b
                .signatureDuration(Duration.ofSeconds(expireSeconds))
                .uploadPartRequest(upr));

        return pre.url().toString();
    }

    @Override
    public CompleteResult completeMultipart(String bucket, String objectKey, String uploadId, List<CompletedPart> parts) {
        List<software.amazon.awssdk.services.s3.model.CompletedPart> completed = parts.stream()
                .map(p -> software.amazon.awssdk.services.s3.model.CompletedPart.builder()
                        .partNumber(p.partNumber())
                        .eTag(p.etag())
                        .build())
                .toList();

        CompletedMultipartUpload cmu = CompletedMultipartUpload.builder()
                .parts(completed)
                .build();

        CompleteMultipartUploadResponse resp = s3.completeMultipartUpload(b -> b
                .bucket(bucket)
                .key(objectKey)
                .uploadId(uploadId)
                .multipartUpload(cmu));

        return new CompleteResult(resp.eTag());
    }

    @Override
    public void abortMultipart(String bucket, String objectKey, String uploadId) {
        s3.abortMultipartUpload(b -> b.bucket(bucket).key(objectKey).uploadId(uploadId));
    }

    @Override
    public String presignGetUrl(String bucket, String objectKey, int expireSeconds) {
        GetObjectRequest gor = GetObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .build();

        PresignedGetObjectRequest pre = preSigner.presignGetObject(b -> b
                .signatureDuration(Duration.ofSeconds(expireSeconds))
                .getObjectRequest(gor));

        return pre.url().toString();
    }

    @Override
    public void deleteObject(String bucket, String objectKey) {
        s3.deleteObject(b -> b.bucket(bucket).key(objectKey));
    }

    @Override
    public CopyResult copyObject(String srcBucket, String srcKey, String destBucket, String destKey) {

        CopyObjectResponse resp = s3.copyObject(b -> b
                        .sourceBucket(srcBucket)
                        .sourceKey(srcKey)
                        .destinationBucket(destBucket)
                        .destinationKey(destKey)
        );

        // 有些实现 etag 在 resp.copyObjectResult().eTag()，不同版本略有差异
        String etag = null;
        if (resp.copyObjectResult() != null) {
            etag = resp.copyObjectResult().eTag();
        }
        return new CopyResult(etag);
    }
}
