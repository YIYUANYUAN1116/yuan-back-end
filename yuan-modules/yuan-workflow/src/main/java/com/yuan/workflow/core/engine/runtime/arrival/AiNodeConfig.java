package com.yuan.workflow.core.engine.runtime.arrival;

import lombok.Data;

@Data
public class AiNodeConfig {

    /**
     * 模式：
     * SUGGEST = 建议
     * ROUTE = 路由
     * 后续如要扩 AUTO_APPROVE，再加
     */
    private String aiMode;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 输出变量前缀
     * 例如 aiReview / aiRoute
     */
    private String outputVarPrefix;

    /**
     * 是否要求 JSON 返回
     */
    private Boolean jsonOutput;

    /**
     * 超时时间（毫秒）
     */
    private Integer timeoutMs;

    /**
     * 失败策略
     * BLOCK / TO_MANUAL
     */
    private String failStrategy;
}