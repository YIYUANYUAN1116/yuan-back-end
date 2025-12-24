package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.yuan.system.domain.SysLogininfor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * loginlog视图对象 sys_logininfor
 *
 * @author ageerle
 * @date Wed Dec 17 21:48:44 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysLogininfor.class)
public class SysLogininforVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long infoId;
    /**
     * 租户编号
     */
    @ExcelProperty(value = "租户编号")
    private String tenantId;
    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userName;
    /**
     * 登录IP地址
     */
    @ExcelProperty(value = "登录IP地址")
    private String ipaddr;
    /**
     * 登录地点
     */
    @ExcelProperty(value = "登录地点")
    private String loginLocation;
    /**
     * 浏览器类型
     */
    @ExcelProperty(value = "浏览器类型")
    private String browser;
    /**
     * 操作系统
     */
    @ExcelProperty(value = "操作系统")
    private String os;
    /**
     * 登录状态（0成功 1失败）
     */
    @ExcelProperty(value = "登录状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String status;
    /**
     * 提示消息
     */
    @ExcelProperty(value = "提示消息")
    private String msg;
    /**
     * 访问时间
     */
    @ExcelProperty(value = "访问时间")
    private LocalDateTime loginTime;

}
