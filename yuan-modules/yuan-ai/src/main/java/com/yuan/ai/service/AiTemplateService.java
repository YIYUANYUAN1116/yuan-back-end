package com.yuan.ai.service;

import java.util.Map;

public interface AiTemplateService {

    /**
     * 根据模板编码获取模板内容
     */
    String getTemplateContent(String templateCode);

    /**
     * 渲染模板
     */
    String render(String templateContent, Map<String, Object> variables);
}