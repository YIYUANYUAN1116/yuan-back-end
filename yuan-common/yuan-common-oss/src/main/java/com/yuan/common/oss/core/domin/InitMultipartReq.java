package com.yuan.common.oss.core.domin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitMultipartReq {
    @NotNull(message = "范围不能为空")
    private OssScope scope;
    @NotBlank(message = "文件名称不能为空")
    private String filename;
    @NotBlank(message = "文件类型不能为空")
    private String contentType;
    @NotNull(message = "文件大小不能为空")
    private Long sizeBytes;
}
