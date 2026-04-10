package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.ChatAttachment;
import com.yuan.ai.domain.bo.ChatAttachmentBo;
import com.yuan.ai.domain.vo.ChatAttachmentVo;
import com.yuan.ai.mapper.ChatAttachmentMapper;
import com.yuan.ai.service.ChatAttachmentService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * chat_attachmentService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:43 CST 2026
 */
@RequiredArgsConstructor
@Service
public class ChatAttachmentServiceImpl implements ChatAttachmentService {

    private final ChatAttachmentMapper baseMapper;

    /**
     * 查询chat_attachment
     */
    @Override
    public ChatAttachmentVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询chat_attachment列表
         */
        @Override
        public TableDataInfo<ChatAttachmentVo> queryPageList(ChatAttachmentBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<ChatAttachment> lqw = buildQueryWrapper(bo);
            Page<ChatAttachmentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询chat_attachment列表
     */
    @Override
    public List<ChatAttachmentVo> queryList(ChatAttachmentBo bo) {
        LambdaQueryWrapper<ChatAttachment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChatAttachment> buildQueryWrapper(ChatAttachmentBo bo) {
        LambdaQueryWrapper<ChatAttachment> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, ChatAttachment::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), ChatAttachment::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getMessageId() != null, ChatAttachment::getMessageId, bo.getMessageId());
                    lqw.eq(StringUtils.isNotBlank(bo.getFileName()), ChatAttachment::getFileName, bo.getFileName());
                    lqw.eq(StringUtils.isNotBlank(bo.getFileType()), ChatAttachment::getFileType, bo.getFileType());
                    lqw.eq(bo.getFileSize() != null, ChatAttachment::getFileSize, bo.getFileSize());
                    lqw.eq(StringUtils.isNotBlank(bo.getStorage()), ChatAttachment::getStorage, bo.getStorage());
                    lqw.eq(StringUtils.isNotBlank(bo.getObjectKey()), ChatAttachment::getObjectKey, bo.getObjectKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getUrl()), ChatAttachment::getUrl, bo.getUrl());
                    lqw.eq(StringUtils.isNotBlank(bo.getMetaJson()), ChatAttachment::getMetaJson, bo.getMetaJson());
                    lqw.eq(bo.getCreateTime() != null, ChatAttachment::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增chat_attachment
     */
    @Override
    public Boolean insertByBo(ChatAttachmentBo bo) {
        ChatAttachment add = MapstructUtils.convert(bo, ChatAttachment. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改chat_attachment
     */
    @Override
    public Boolean updateByBo(ChatAttachmentBo bo) {
        ChatAttachment update = MapstructUtils.convert(bo, ChatAttachment. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChatAttachment entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除chat_attachment
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
