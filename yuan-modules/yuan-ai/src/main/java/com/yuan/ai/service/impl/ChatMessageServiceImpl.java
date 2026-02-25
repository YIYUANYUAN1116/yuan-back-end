package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.ChatMessage;
import com.yuan.ai.domain.bo.ChatMessageBo;
import com.yuan.ai.domain.model.ChatRequest;
import com.yuan.ai.domain.vo.ChatMessageVo;
import com.yuan.ai.mapper.ChatMessageMapper;
import com.yuan.ai.service.ChatMessageService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * chat-messageService业务层处理
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:03 CST 2026
 */
@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageMapper baseMapper;

    /**
     * 查询chat-message
     */
    @Override
    public ChatMessageVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询chat-message列表
         */
        @Override
        public TableDataInfo<ChatMessageVo> queryPageList(ChatMessageBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<ChatMessage> lqw = buildQueryWrapper(bo);
            Page<ChatMessageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询chat-message列表
     */
    @Override
    public List<ChatMessageVo> queryList(ChatMessageBo bo) {
        LambdaQueryWrapper<ChatMessage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChatMessage> buildQueryWrapper(ChatMessageBo bo) {
        LambdaQueryWrapper<ChatMessage> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, ChatMessage::getId, bo.getId());
                    lqw.eq(bo.getSessionId() != null, ChatMessage::getSessionId, bo.getSessionId());
                    lqw.eq(bo.getUserId() != null, ChatMessage::getUserId, bo.getUserId());
                    lqw.eq(StringUtils.isNotBlank(bo.getContent()), ChatMessage::getContent, bo.getContent());
                    lqw.eq(StringUtils.isNotBlank(bo.getRole()), ChatMessage::getRole, bo.getRole());
                    lqw.eq(bo.getDeductCost() != null, ChatMessage::getDeductCost, bo.getDeductCost());
                    lqw.eq(bo.getTotalTokens() != null, ChatMessage::getTotalTokens, bo.getTotalTokens());
                    lqw.eq(StringUtils.isNotBlank(bo.getModelName()), ChatMessage::getModelName, bo.getModelName());
                    lqw.eq(bo.getCreateDept() != null, ChatMessage::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, ChatMessage::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, ChatMessage::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, ChatMessage::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, ChatMessage::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getRemark()), ChatMessage::getRemark, bo.getRemark());
                    lqw.eq(StringUtils.isNotBlank(bo.getBillingType()), ChatMessage::getBillingType, bo.getBillingType());
        return lqw;
    }

    /**
     * 新增chat-message
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
     * 修改chat-message
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
     * 批量删除chat-message
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
    @Override
    public long insertUserMessage(ChatRequest req, String userContent) {
        ChatMessage m = new ChatMessage();
        m.setSessionId(req.getSessionId());
        m.setUserId(req.getUserId());
        m.setRole("user");
        m.setContent(userContent);
        m.setModelName(req.getModelKey()); // 先记 modelKey
        m.setTotalTokens(0);
        m.setDeductCost(BigDecimal.valueOf(0.0));
        m.setCreateTime(LocalDateTime.now());
        baseMapper.insert(m);
        return m.getId();
    }
    @Override
    public long insertAssistantPlaceholder(ChatRequest req, String modelNameToSave) {
        ChatMessage m = new ChatMessage();
        m.setSessionId(req.getSessionId());
        m.setUserId(req.getUserId());
        m.setRole("assistant");
        m.setContent("");
        m.setModelName(modelNameToSave);
        m.setTotalTokens(0);
        m.setDeductCost(BigDecimal.valueOf(0.0));
        m.setCreateTime(LocalDateTime.now());
        baseMapper.insert(m);
        return m.getId();
    }
    @Override
    public void updateAssistantContent(long messageId, String content) {
        ChatMessage upd = new ChatMessage();
        upd.setId(messageId);
        upd.setContent(content);
        upd.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(upd);
    }
}
