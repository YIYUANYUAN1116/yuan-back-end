package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * post对象 sys_post
 *
 
 * @date Mon Dec 22 15:05:40 CST 2025
 */
@Data
@TableName("sys_post")
public class SysPost extends BaseEntity implements Serializable {


    /**
     * 岗位ID
     */
        @TableId(value = "post_id", type = IdType.AUTO)
    private Long postId;

    /**
     * 租户编号
     */
    private String tenantId;

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

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    private String dataScope;

}
