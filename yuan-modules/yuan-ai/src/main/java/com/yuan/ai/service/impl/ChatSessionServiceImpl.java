package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.ChatSession;
import com.yuan.ai.domain.bo.ChatSessionBo;
import com.yuan.ai.domain.vo.ChatSessionVo;
import com.yuan.ai.mapper.ChatSessionMapper;
import com.yuan.ai.service.ChatSessionService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * chat-sessionService业务层处理
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:22 CST 2026
 */
@RequiredArgsConstructor
@Service
public class ChatSessionServiceImpl implements ChatSessionService {

    private final ChatSessionMapper baseMapper;

    /**
     * 查询chat-session
     */
    @Override
    public ChatSessionVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询chat-session列表
         */
        @Override
        public TableDataInfo<ChatSessionVo> queryPageList(ChatSessionBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<ChatSession> lqw = buildQueryWrapper(bo);
            Page<ChatSessionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询chat-session列表
     */
    @Override
    public List<ChatSessionVo> queryList(ChatSessionBo bo) {
        LambdaQueryWrapper<ChatSession> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChatSession> buildQueryWrapper(ChatSessionBo bo) {
        LambdaQueryWrapper<ChatSession> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, ChatSession::getId, bo.getId());
                    lqw.eq(bo.getUserId() != null, ChatSession::getUserId, bo.getUserId());
                    lqw.eq(StringUtils.isNotBlank(bo.getSessionTitle()), ChatSession::getSessionTitle, bo.getSessionTitle());
                    lqw.eq(StringUtils.isNotBlank(bo.getSessionContent()), ChatSession::getSessionContent, bo.getSessionContent());
                    lqw.eq(StringUtils.isNotBlank(bo.getCreateDept()), ChatSession::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, ChatSession::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, ChatSession::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, ChatSession::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, ChatSession::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getRemark()), ChatSession::getRemark, bo.getRemark());
                    lqw.eq(StringUtils.isNotBlank(bo.getConversationId()), ChatSession::getConversationId, bo.getConversationId());
        return lqw;
    }

    /**
     * 新增chat-session
     */
    @Override
    public Boolean insertByBo(ChatSessionBo bo) {
        ChatSession add = MapstructUtils.convert(bo, ChatSession. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改chat-session
     */
    @Override
    public Boolean updateByBo(ChatSessionBo bo) {
        ChatSession update = MapstructUtils.convert(bo, ChatSession. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChatSession entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除chat-session
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
