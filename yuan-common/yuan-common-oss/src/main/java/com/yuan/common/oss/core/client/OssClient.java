package com.yuan.common.oss.core.client;

import com.yuan.common.oss.core.domin.FileObjectKey;
import com.yuan.common.oss.core.domin.MultipartSession;
import com.yuan.common.oss.core.domin.ObjectKeyGenerator;
import com.yuan.common.oss.core.domin.OssScope;
import com.yuan.common.oss.core.provider.StorageProvider;
import com.yuan.common.oss.core.provider.StorageProviderRouter;
import com.yuan.common.oss.properties.OssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OssClient {

    private final OssProperties props;
    private final StorageProviderRouter router;
    private final ObjectKeyGenerator keyGen;


    public FileObjectKey putObject(OssScope scope, MultipartFile file) throws IOException {
        return putObject(scope,file.getOriginalFilename(),file.getContentType(),file.getSize(),file.getBytes());
    }

    public FileObjectKey putObject(OssScope scope, String filename, String contentType, long sizeBytes, byte[] bytes) {
        String objectKey = keyGen.generate(scope, filename);
        StorageProvider.PutResult r = router.current().putObject(props.getBucket(), objectKey, contentType, bytes);

        return FileObjectKey.builder()
                .storage(router.current().type())
                .bucket(props.getBucket())
                .objectKey(objectKey)
                .etag(r.etag())
                .sizeBytes(sizeBytes)
                .contentType(contentType)
                .filename(filename)
                .build();
    }


    public MultipartSession initMultipart(OssScope scope, String filename, String contentType, long sizeBytes) {
        String objectKey = keyGen.generate(scope, filename);

        StorageProvider.MultipartInitResult init = router.current()
                .initMultipart(props.getBucket(), objectKey, contentType);

        int partSizeBytes = props.getMultipart().getPartSizeMb() * 1024 * 1024;
        int totalParts = (int) Math.ceil((double) sizeBytes / partSizeBytes);

        MultipartSession s = new MultipartSession();
        s.setSessionId(init.uploadId()); // 无状态最小实现
        s.setUploadId(init.uploadId());
        s.setBucket(props.getBucket());
        s.setObjectKey(objectKey);
        s.setPartSizeBytes(partSizeBytes);
        s.setTotalParts(totalParts);
        return s;
    }

    public String presignUploadPartUrl(MultipartSession session, int partNumber) {
        return router.current().presignUploadPartUrl(
                session.getBucket(), session.getObjectKey(), session.getUploadId(),
                partNumber, props.getPresignExpireSeconds()
        );
    }

    public FileObjectKey completeMultipart(String bucket, String objectKey, String uploadId,
                                           List<StorageProvider.CompletedPart> parts,
                                           String filename, String contentType, long sizeBytes) {
        StorageProvider.CompleteResult cr = router.current()
                .completeMultipart(bucket, objectKey, uploadId, parts);

        return FileObjectKey.builder()
                .storage(router.current().type())
                .bucket(bucket)
                .objectKey(objectKey)
                .etag(cr.etag())
                .sizeBytes(sizeBytes)
                .contentType(contentType)
                .filename(filename)
                .build();
    }

    public void abortMultipart(String bucket, String objectKey, String uploadId) {
        router.current().abortMultipart(bucket, objectKey, uploadId);
    }

    public String presignGet(String objectKey) {
        FileObjectKey key = FileObjectKey.builder()
                .storage(router.current().type())
                .bucket(props.getBucket())
                .objectKey(objectKey)
                .build();
        return presignGet(key,null);
    }

    public String presignGet(FileObjectKey key) {
        return presignGet(key,null);
    }

    public String presignGet(FileObjectKey key, Integer expireSeconds) {
        int exp = (expireSeconds == null || expireSeconds <= 0)
                ? props.getPresignExpireSeconds()
                : expireSeconds;
        return router.current().presignGetUrl(key.getBucket(), key.getObjectKey(), exp);
    }

    public void delete(FileObjectKey key) {
        router.current().deleteObject(key.getBucket(), key.getObjectKey());
    }

    public FileObjectKey copyObject(FileObjectKey from, OssScope toScope, String filenameOverride) {
        String filename = (filenameOverride != null && !filenameOverride.isBlank())
                ? filenameOverride
                : from.getFilename();

        String destKey = keyGen.generate(toScope, filename);

        StorageProvider.CopyResult cr = router.current().copyObject(
                from.getBucket(), from.getObjectKey(),
                from.getBucket(), destKey
        );

        return FileObjectKey.builder()
                .storage(from.getStorage()) // 仍然同一种 provider/存储（同 bucket 下）
                .bucket(from.getBucket())
                .objectKey(destKey)
                .etag(cr.etag())
                .sizeBytes(from.getSizeBytes())
                .contentType(from.getContentType())
                .filename(filename)
                .sha256(from.getSha256())
                .build();
    }

    /**
     * move：先 copy 成功，再 delete 源对象（temp -> formal 最常用）
     * 业务侧把更新 DB 放在事务里：copy成功 -> 更新业务表key -> 再删源
     */
    public FileObjectKey moveObject(FileObjectKey from, OssScope toScope, String filenameOverride) {
        FileObjectKey dest = copyObject(from, toScope, filenameOverride);

        // 删除源对象
        router.current().deleteObject(from.getBucket(), from.getObjectKey());

        return dest;
    }
}
