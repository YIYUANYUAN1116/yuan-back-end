package com.yuan.ai.service;

import com.yuan.ai.domain.vo.ChatSessionVo;
import com.yuan.ai.domain.bo.ChatSessionBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * chat-sessionService接口
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:22 CST 2026
 */
public interface ChatSessionService {

    /**
     * 查询chat-session
     */
        ChatSessionVo queryById(Long id);

        /**
         * 查询chat-session列表
         */
        TableDataInfo<ChatSessionVo> queryPageList(ChatSessionBo bo, PageQuery pageQuery);

    /**
     * 查询chat-session列表
     */
    List<ChatSessionVo> queryList(ChatSessionBo bo);

    /**
     * 新增chat-session
     */
    Boolean insertByBo(ChatSessionBo bo);

    /**
     * 修改chat-session
     */
    Boolean updateByBo(ChatSessionBo bo);

    /**
     * 校验并批量删除chat-session信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
