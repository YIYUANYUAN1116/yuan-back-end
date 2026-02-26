package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * llm_provider对象 llm_provider
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
@Data
@TableName("llm_provider")
public class LlmProvider implements Serializable {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * OPENAI_COMPAT/AZURE/OLLAMA/SELF_HOST
     */
    private String code;

    /**
     * name
     */
    private String name;

    /**
     * openai_compat/azure/ollama/...
     */
    private String protocol;

    /**
     * createTime
     */
    private LocalDateTime createTime;


}
