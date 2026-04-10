package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.ChatConversation;
import com.yuan.ai.domain.bo.ChatConversationBo;
import com.yuan.ai.domain.vo.ChatConversationVo;
import com.yuan.ai.mapper.ChatConversationMapper;
import com.yuan.ai.service.ChatConversationService;
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
 * chat_conversationService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:35 CST 2026
 */
@RequiredArgsConstructor
@Service
public class ChatConversationServiceImpl implements ChatConversationService {

    private final ChatConversationMapper baseMapper;

    /**
     * 查询chat_conversation
     */
    @Override
    public ChatConversationVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询chat_conversation列表
         */
        @Override
        public TableDataInfo<ChatConversationVo> queryPageList(ChatConversationBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<ChatConversation> lqw = buildQueryWrapper(bo);
            Page<ChatConversationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询chat_conversation列表
     */
    @Override
    public List<ChatConversationVo> queryList(ChatConversationBo bo) {
        LambdaQueryWrapper<ChatConversation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChatConversation> buildQueryWrapper(ChatConversationBo bo) {
        LambdaQueryWrapper<ChatConversation> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, ChatConversation::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), ChatConversation::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getUserId() != null, ChatConversation::getUserId, bo.getUserId());
                    lqw.eq(bo.getAppId() != null, ChatConversation::getAppId, bo.getAppId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTitle()), ChatConversation::getTitle, bo.getTitle());
                    lqw.eq(StringUtils.isNotBlank(bo.getMetaJson()), ChatConversation::getMetaJson, bo.getMetaJson());
                    lqw.eq(bo.getLastMessageAt() != null, ChatConversation::getLastMessageAt, bo.getLastMessageAt());
                    lqw.eq(bo.getCreateTime() != null, ChatConversation::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateTime() != null, ChatConversation::getUpdateTime, bo.getUpdateTime());
        return lqw;
    }

    /**
     * 新增chat_conversation
     */
    @Override
    public Boolean insertByBo(ChatConversationBo bo) {
        ChatConversation add = MapstructUtils.convert(bo, ChatConversation. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改chat_conversation
     */
    @Override
    public Boolean updateByBo(ChatConversationBo bo) {
        ChatConversation update = MapstructUtils.convert(bo, ChatConversation. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChatConversation entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除chat_conversation
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public ChatConversation getOrCreate(String tenantId, Long conversationId, Long userId, Long appId, Long modelId,String title) {
        if (conversationId != null) {
            ChatConversation c = baseMapper.selectById(conversationId);
            if (c != null) return c;
        }
        ChatConversation c = new ChatConversation();
        c.setTenantId(tenantId);
        c.setUserId(userId == null ? 0L : userId);
        c.setAppId(appId);
        c.setTitle(title);
        c.setModelId(modelId);
        c.setCreateTime(LocalDateTime.now());
        c.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(c);
        return c;
    }

    @Override
    public void touch(Long conversationId) {
        ChatConversation upd = new ChatConversation();
        upd.setId(conversationId);
        upd.setLastMessageAt(LocalDateTime.now());
        baseMapper.updateById(upd);
    }
}
