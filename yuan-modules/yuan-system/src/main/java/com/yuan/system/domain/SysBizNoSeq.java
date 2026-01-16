package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.tenant.core.TenantEntity;
import lombok.Data;

/**
 * 业务序列表对象 sys_biz_no_seq
 *
 * @author yuan
 * @date Fri Jan 16 16:53:43 CST 2026
 */
@Data
@TableName("sys_biz_no_seq")
public class SysBizNoSeq extends TenantEntity {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 业务前缀，如 OA / EXP / CON
     */
    private String bizPrefix;

    /**
     * 业务日期 yyyyMMdd
     */
    private String bizDate;

    /**
     * 当前序号
     */
    private Integer currentNo;

}
