package com.yuan.system.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.system.domain.SysBizNoSeq;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务序列表业务对象 sys_biz_no_seq
 *
 * @author yuan
 * @date Fri Jan 16 16:53:43 CST 2026
 */
@Data

@AutoMapper(target = SysBizNoSeq.class, reverseConvertGenerate = false)
public class SysBizNoSeqBo implements Serializable {

    private Long id;

    /**
     * 租户Id
     */
    private String tenantId;
    /**
     * 业务前缀，如 OA / EXP / CON
     */
    @NotBlank(message = "业务前缀，如 OA / EXP / CON不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bizPrefix;
    /**
     * 业务日期 yyyyMMdd
     */
    @NotBlank(message = "业务日期 yyyyMMdd不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bizDate;
    /**
     * 当前序号
     */
    @NotNull(message = "当前序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer currentNo;
    /**
     * updateTime
     */
    private LocalDateTime updateTime;
    /**
     * 创建部门
     */
    private Long createDept;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    private Long updateBy;

}
