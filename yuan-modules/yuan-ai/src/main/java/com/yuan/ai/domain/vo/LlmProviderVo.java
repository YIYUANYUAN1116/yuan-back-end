package com.yuan.ai.domain.vo;

    import java.time.LocalDateTime;
    import java.io.Serializable;
import com.yuan.ai.domain.LlmProvider;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;


/**
 * llm_provider视图对象 llm_provider
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LlmProvider.class)
public class LlmProviderVo implements Serializable {

    private Long id;
    /**
     * OPENAI_COMPAT/AZURE/OLLAMA/SELF_HOST
     */
    @ExcelProperty(value = "OPENAI_COMPAT/AZURE/OLLAMA/SELF_HOST")
    private String code;
    /**
     * name
     */
    @ExcelProperty(value = "name")
    private String name;
    /**
     * openai_compat/azure/ollama/...
     */
    @ExcelProperty(value = "openai_compat/azure/ollama/...")
    private String protocol;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;

}
