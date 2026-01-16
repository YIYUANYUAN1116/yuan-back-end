package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.system.domain.SysBizNoSeq;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 业务序列表视图对象 sys_biz_no_seq
 *
 * @author yuan
 * @date Fri Jan 16 16:53:43 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysBizNoSeq.class)
public class SysBizNoSeqVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    /**
     * 租户Id
     */
    @ExcelProperty(value = "租户Id")
    private String tenantId;
    /**
     * 业务前缀，如 OA / EXP / CON
     */
    @ExcelProperty(value = "业务前缀，如 OA / EXP / CON")
    private String bizPrefix;
    /**
     * 业务日期 yyyyMMdd
     */
    @ExcelProperty(value = "业务日期 yyyyMMdd")
    private String bizDate;
    /**
     * 当前序号
     */
    @ExcelProperty(value = "当前序号")
    private Integer currentNo;
    /**
     * updateTime
     */
    @ExcelProperty(value = "updateTime")
    private LocalDateTime updateTime;
    /**
     * 创建部门
     */
    @ExcelProperty(value = "创建部门")
    private Long createDept;
    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private Long createBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    private Long updateBy;

}
