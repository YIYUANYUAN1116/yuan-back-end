package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.ChatMessage;
import com.yuan.ai.domain.bo.ChatMessageBo;
import com.yuan.ai.domain.vo.ChatMessageVo;
import com.yuan.ai.mapper.ChatMessageMapper;
import com.yuan.ai.service.ChatMessageService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * chat_messageService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:39 CST 2026
 */
@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageMapper baseMapper;

    /**
     * 查询chat_message
     */
    @Override
    public ChatMessageVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询chat_message列表
         */
        @Override
        public TableDataInfo<ChatMessageVo> queryPageList(ChatMessageBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<ChatMessage> lqw = buildQueryWrapper(bo);
            Page<ChatMessageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询chat_message列表
     */
    @Override
    public List<ChatMessageVo> queryList(ChatMessageBo bo) {
        LambdaQueryWrapper<ChatMessage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChatMessage> buildQueryWrapper(ChatMessageBo bo) {
        LambdaQueryWrapper<ChatMessage> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, ChatMessage::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), ChatMessage::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getConversationId() != null, ChatMessage::getConversationId, bo.getConversationId());
                    lqw.eq(bo.getUserId() != null, ChatMessage::getUserId, bo.getUserId());
                    lqw.eq(StringUtils.isNotBlank(bo.getRole()), ChatMessage::getRole, bo.getRole());
                    lqw.eq(StringUtils.isNotBlank(bo.getContent()), ChatMessage::getContent, bo.getContent());
                    lqw.eq(StringUtils.isNotBlank(bo.getContentFormat()), ChatMessage::getContentFormat, bo.getContentFormat());
                    lqw.eq(bo.getParentId() != null, ChatMessage::getParentId, bo.getParentId());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), ChatMessage::getStatus, bo.getStatus());
                    lqw.eq(bo.getInvocationId() != null, ChatMessage::getInvocationId, bo.getInvocationId());
                    lqw.eq(bo.getTokenIn() != null, ChatMessage::getTokenIn, bo.getTokenIn());
                    lqw.eq(bo.getTokenOut() != null, ChatMessage::getTokenOut, bo.getTokenOut());
                    lqw.eq(bo.getCostAmount() != null, ChatMessage::getCostAmount, bo.getCostAmount());
                    lqw.eq(StringUtils.isNotBlank(bo.getFinishReason()), ChatMessage::getFinishReason, bo.getFinishReason());
                    lqw.eq(StringUtils.isNotBlank(bo.getErrorMsg()), ChatMessage::getErrorMsg, bo.getErrorMsg());
                    lqw.eq(bo.getCreateTime() != null, ChatMessage::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateTime() != null, ChatMessage::getUpdateTime, bo.getUpdateTime());

        lqw.orderByAsc(ChatMessage::getCreateTime);
        lqw.orderByAsc(ChatMessage::getId);
        return lqw;
    }

    /**
     * 新增chat_message
     */
    @Override
    public Boolean insertByBo(ChatMessageBo bo) {
        ChatMessage add = MapstructUtils.convert(bo, ChatMessage. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改chat_message
     */
    @Override
    public Boolean updateByBo(ChatMessageBo bo) {
        ChatMessage update = MapstructUtils.convert(bo, ChatMessage. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChatMessage entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除chat_message
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public long insertUserMsg(String tenantId, long conversationId, Long userId, Long modelId, String content) {
        ChatMessage m = new ChatMessage();
        m.setTenantId(tenantId);
        m.setConversationId(conversationId);
        m.setUserId(userId == null ? 0L : userId);
        m.setRole("user");
        m.setContent(content);
        m.setContentFormat("text");
        m.setStatus("DONE");
        m.setModelId(modelId);
        m.setCreateTime(LocalDateTime.now());
        m.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(m);
        return m.getId();
    }

    @Override
    public long insertAssistantPlaceholder(String tenantId, long conversationId, Long userId, Long modelId) {
        ChatMessage m = new ChatMessage();
        m.setTenantId(tenantId);
        m.setConversationId(conversationId);
        m.setUserId(userId == null ? 0L : userId);
        m.setRole("assistant");
        m.setContent("");
        m.setContentFormat("text");
        m.setStatus("STREAMING");
        m.setModelId(modelId);
        m.setCreateTime(LocalDateTime.now());
        m.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(m);
        return m.getId();
    }

    @Override
    public void bindInvocation(long messageId, long invocationId) {
        ChatMessage upd = new ChatMessage();
        upd.setId(messageId);
        upd.setInvocationId(invocationId);
        upd.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(upd);
    }

    @Override
    public void finishAssistant(long messageId, String content) {
        ChatMessage upd = new ChatMessage();
        upd.setId(messageId);
        upd.setContent(content);
        upd.setStatus("DONE");
        upd.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(upd);
    }

    @Override
    public void failAssistant(long messageId, String partial, String errorMsg) {
        ChatMessage upd = new ChatMessage();
        upd.setId(messageId);
        upd.setContent(partial);
        upd.setStatus("FAILED");
        upd.setErrorMsg(errorMsg);
        upd.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(upd);
    }
}
