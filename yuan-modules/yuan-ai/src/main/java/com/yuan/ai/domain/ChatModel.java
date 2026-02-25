package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.ai.chat.messages.Message;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * chat_model对象 chat_model
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:14 CST 2026
 */
@Data
@TableName("chat_model")
public class ChatModel implements Serializable {

    private Message message;
    /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 模型分类
     */
    private String category;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型供应商
     */
    private String providerName;

    /**
     * 模型描述
     */
    private String modelDescribe;

    /**
     * 模型价格
     */
    private BigDecimal modelPrice;

    /**
     * 计费类型
     */
    private String modelType;

    /**
     * 是否显示
     */
    private String modelShow;

    /**
     * 系统提示词
     */
    private String systemPrompt;

    /**
     * 请求地址
     */
    private String apiHost;

    /**
     * 密钥
     */
    private String apiKey;

    /**
     * 请求后缀
     */
    private String apiUrl;

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

    /**
     * 模型优先级(值越大优先级越高)
     */
    private Integer priority;

    private String modelKey;
    private String remoteModel;
    private String providerType;
    private String enabled;
    private String supportStream;
    private String supportJson;
    private String baseUrl;

}
