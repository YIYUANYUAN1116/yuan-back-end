package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.core.domain.BaseEntity;
import lombok.Data;

/**
 * llm_provider对象 llm_provider
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
@Data
@TableName("llm_provider")
public class LlmProvider extends BaseEntity {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * OPENAI_COMPAT/AZURE/OLLAMA/SELF_HOST
     */
    private String providerCode;

    /**
     * name
     */
    private String providerName;

    /**
     * openai_compat/azure/ollama/...
     */
    private String protocol;

    private String remark;
}
