package com.yuan.system.domain.bo;

import com.yuan.common.log.event.LogininforEvent;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import com.yuan.system.domain.SysLogininfor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * loginlog业务对象 sys_logininfor
 *
 * @author ageerle
 * @date Wed Dec 17 21:48:44 CST 2025
 */
@Data
@AutoMappers({
        @AutoMapper(target = SysLogininfor.class, reverseConvertGenerate = false),
        @AutoMapper(target = LogininforEvent.class)
})

public class SysLogininforBo implements Serializable {

    private Long infoId;

    /**
     * 租户编号
     */
    private String tenantId;
    /**
     * 用户Id
     */
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

    private boolean onlySelf;
}
