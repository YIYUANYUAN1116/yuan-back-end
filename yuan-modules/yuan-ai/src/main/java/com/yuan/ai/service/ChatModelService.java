package com.yuan.ai.service;

import com.yuan.ai.domain.vo.ChatModelVo;
import com.yuan.ai.domain.bo.ChatModelBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * chat_modelService接口
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:14 CST 2026
 */
public interface ChatModelService {

    /**
     * 查询chat_model
     */
        ChatModelVo queryById(Long id);

        /**
         * 查询chat_model列表
         */
        TableDataInfo<ChatModelVo> queryPageList(ChatModelBo bo, PageQuery pageQuery);

    /**
     * 查询chat_model列表
     */
    List<ChatModelVo> queryList(ChatModelBo bo);

    /**
     * 新增chat_model
     */
    Boolean insertByBo(ChatModelBo bo);

    /**
     * 修改chat_model
     */
    Boolean updateByBo(ChatModelBo bo);

    /**
     * 校验并批量删除chat_model信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
