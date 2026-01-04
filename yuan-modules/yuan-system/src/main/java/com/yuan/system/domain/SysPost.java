package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.tenant.core.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * post对象 sys_post
 *
 
 * @date Mon Dec 22 15:05:40 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_post")
public class SysPost extends TenantEntity  {


    /**
     * 岗位ID
     */
        @TableId(value = "post_id", type = IdType.AUTO)
    private Long postId;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 显示顺序
     */
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    private Long deptId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
}
