package com.yuan.ai.service;

import com.yuan.ai.domain.vo.ChatAttachmentVo;
import com.yuan.ai.domain.bo.ChatAttachmentBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * chat_attachmentService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:43 CST 2026
 */
public interface ChatAttachmentService {

    /**
     * 查询chat_attachment
     */
        ChatAttachmentVo queryById(Long id);

        /**
         * 查询chat_attachment列表
         */
        TableDataInfo<ChatAttachmentVo> queryPageList(ChatAttachmentBo bo, PageQuery pageQuery);

    /**
     * 查询chat_attachment列表
     */
    List<ChatAttachmentVo> queryList(ChatAttachmentBo bo);

    /**
     * 新增chat_attachment
     */
    Boolean insertByBo(ChatAttachmentBo bo);

    /**
     * 修改chat_attachment
     */
    Boolean updateByBo(ChatAttachmentBo bo);

    /**
     * 校验并批量删除chat_attachment信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
