package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * post对象 sys_post
 *
 * @author ageerle
 * @date Mon Dec 22 15:05:40 CST 2025
 */
@Data
@TableName("sys_post")
public class SysPost implements Serializable {


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

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;


}
