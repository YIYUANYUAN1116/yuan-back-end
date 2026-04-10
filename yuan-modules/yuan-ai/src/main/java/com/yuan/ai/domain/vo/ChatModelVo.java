package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.ChatModel;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * chat_model视图对象 chat_model
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:14 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChatModel.class)
public class ChatModelVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
     private Long id;
    /**
     * 租户编号
     */
    @ExcelProperty(value = "租户编号")
    private String tenantId;
    /**
     * 模型分类
     */
    @ExcelProperty(value = "模型分类")
    private String category;
    /**
     * 模型名称
     */
    @ExcelProperty(value = "模型名称")
    private String modelName;
    /**
     * 模型供应商
     */
    @ExcelProperty(value = "模型供应商")
    private String providerName;
    /**
     * 模型描述
     */
    @ExcelProperty(value = "模型描述")
    private String modelDescribe;
    /**
     * 模型价格
     */
    @ExcelProperty(value = "模型价格")
    private BigDecimal modelPrice;
    /**
     * 计费类型
     */
    @ExcelProperty(value = "计费类型")
    private String modelType;
    /**
     * 是否显示
     */
    @ExcelProperty(value = "是否显示")
    private String modelShow;
    /**
     * 系统提示词
     */
    @ExcelProperty(value = "系统提示词")
    private String systemPrompt;
    /**
     * 请求地址
     */
    @ExcelProperty(value = "请求地址")
    private String apiHost;
    /**
     * 密钥
     */
    @ExcelProperty(value = "密钥")
    private String apiKey;
    /**
     * 请求后缀
     */
    @ExcelProperty(value = "请求后缀")
    private String apiUrl;
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
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * 模型优先级(值越大优先级越高)
     */
    @ExcelProperty(value = "模型优先级(值越大优先级越高)")
    private Integer priority;

}
