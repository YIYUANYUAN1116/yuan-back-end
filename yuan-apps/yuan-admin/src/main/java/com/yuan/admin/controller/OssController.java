package com.yuan.admin.controller;

import com.yuan.common.core.bizno.BizNoPrefixEnum;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.exception.base.BaseException;
import com.yuan.common.oss.core.client.OssClient;
import com.yuan.common.oss.core.domin.CompleteMultipartReq;
import com.yuan.common.oss.core.domin.FileObjectKey;
import com.yuan.common.oss.core.domin.InitMultipartReq;
import com.yuan.common.oss.core.domin.MultipartSession;
import com.yuan.common.oss.core.domin.OssScope;
import com.yuan.common.oss.core.provider.StorageProvider;
import com.yuan.common.oss.enums.NameSpace;
import com.yuan.common.satoken.utils.LoginHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/oss")
@Tag(name = "OssController",description = "oss接口")
public class OssController {
    private final OssClient ossClient;

    @PostMapping("/temp/upload")
    private R<Void> tempUpload(@RequestPart("avatarFile") MultipartFile file, @RequestParam("bizPrefix") String bizPrefix) {
        if (file == null || file.isEmpty()) {
            throw new BaseException("请选择文件");
        }
        try {
            OssScope scope = OssScope.builder()
                    .prefix(BizNoPrefixEnum.findByPrefix(bizPrefix))
                    .tenantId(LoginHelper.getTenantId())
                    .namespace(NameSpace.TEMP)
                    .build();
            FileObjectKey key = ossClient.putObject(scope, file);
            return R.ok(ossClient.presignGet(key.getObjectKey()));
        } catch (IOException e) {
            log.error("[OssController][tempUpload] upload file error", e);
            throw new BaseException("文件上传失败");
        }
    }

    @PostMapping("multipart/init")
    private R<MultipartSession> initMultipart(@RequestBody InitMultipartReq initMultipart) {
        OssScope scope = initMultipart.getScope();
        scope.setTenantId(LoginHelper.getTenantId());
        scope.setNamespace(NameSpace.TEMP);
        MultipartSession multipart = ossClient.initMultipart(
                scope,
                initMultipart.getFilename(),
                initMultipart.getContentType(),
                initMultipart.getSizeBytes());
        return R.ok(multipart);
    }

    @PostMapping("/multipart/part-url")
    private R<String> multipartPartUrl(@RequestBody MultipartSession multipart) {
        String partUrl = ossClient.presignUploadPartUrl(multipart, multipart.getCurrentPartNumber());
        return R.ok(partUrl);
    }


    @PostMapping("/multipart/complete")
    private R<FileObjectKey> multipartComplete(@RequestBody CompleteMultipartReq req) {
        List<StorageProvider.CompletedPart> parts =
                req.getParts().stream()
                        .map(item -> new StorageProvider.CompletedPart(item.getPartNumber(), item.getEtag()))
                        .toList();
        FileObjectKey key = ossClient.completeMultipart(
                req.getBucket(),
                req.getObjectKey(),
                req.getUploadId(),
                parts,
                req.getFilename(),
                req.getContentType(),
                req.getSizeBytes());
        return R.ok(key);
    }
}
