package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.ChatMessageChunk;
import com.yuan.ai.domain.bo.ChatMessageChunkBo;
import com.yuan.ai.domain.vo.ChatMessageChunkVo;
import com.yuan.ai.mapper.ChatMessageChunkMapper;
import com.yuan.ai.service.ChatMessageChunkService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * chat_message_chunkService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:46 CST 2026
 */
@RequiredArgsConstructor
@Service
public class ChatMessageChunkServiceImpl implements ChatMessageChunkService {

    private final ChatMessageChunkMapper baseMapper;

    /**
     * 查询chat_message_chunk
     */
    @Override
    public ChatMessageChunkVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询chat_message_chunk列表
         */
        @Override
        public TableDataInfo<ChatMessageChunkVo> queryPageList(ChatMessageChunkBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<ChatMessageChunk> lqw = buildQueryWrapper(bo);
            Page<ChatMessageChunkVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询chat_message_chunk列表
     */
    @Override
    public List<ChatMessageChunkVo> queryList(ChatMessageChunkBo bo) {
        LambdaQueryWrapper<ChatMessageChunk> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChatMessageChunk> buildQueryWrapper(ChatMessageChunkBo bo) {
        LambdaQueryWrapper<ChatMessageChunk> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, ChatMessageChunk::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), ChatMessageChunk::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getMessageId() != null, ChatMessageChunk::getMessageId, bo.getMessageId());
                    lqw.eq(bo.getSeq() != null, ChatMessageChunk::getSeq, bo.getSeq());
                    lqw.eq(StringUtils.isNotBlank(bo.getDeltaText()), ChatMessageChunk::getDeltaText, bo.getDeltaText());
                    lqw.eq(bo.getCreateTime() != null, ChatMessageChunk::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增chat_message_chunk
     */
    @Override
    public Boolean insertByBo(ChatMessageChunkBo bo) {
        ChatMessageChunk add = MapstructUtils.convert(bo, ChatMessageChunk. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改chat_message_chunk
     */
    @Override
    public Boolean updateByBo(ChatMessageChunkBo bo) {
        ChatMessageChunk update = MapstructUtils.convert(bo, ChatMessageChunk. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChatMessageChunk entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除chat_message_chunk
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
