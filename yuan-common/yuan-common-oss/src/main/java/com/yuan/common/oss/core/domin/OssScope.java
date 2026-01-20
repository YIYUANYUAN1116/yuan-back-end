package com.yuan.common.oss.core.domin;

import com.yuan.common.core.bizno.BizNoPrefixEnum;
import com.yuan.common.oss.enums.NameSpace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssScope {
    private String tenantId;      // 必填
    private NameSpace namespace;   // temp / formal / public（业务传）
    private BizNoPrefixEnum prefix;      // 业务自定义目录字符串，比如 "oa/leave"（可空）
}
