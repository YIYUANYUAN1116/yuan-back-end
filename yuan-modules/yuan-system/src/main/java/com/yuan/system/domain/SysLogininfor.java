package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * loginlog对象 sys_logininfor
 *
 
 * @date Wed Dec 17 21:48:44 CST 2025
 */
@Data
@TableName("sys_logininfor")
public class SysLogininfor implements Serializable {


    /**
     * 访问ID
     */
        @TableId(value = "info_Id", type = IdType.AUTO)
    private Long infoId;

    /**
     * 租户编号
     */
    private String tenantId;

    private Long userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    private String status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private LocalDateTime loginTime;


}
