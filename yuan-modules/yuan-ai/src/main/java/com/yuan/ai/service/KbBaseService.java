package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbBaseVo;
import com.yuan.ai.domain.bo.KbBaseBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库主表Service接口
 *
 * @author yuan
 * @date Fri May 01 16:08:21 CST 2026
 */
public interface KbBaseService {

    /**
     * 查询知识库主表
     */
        KbBaseVo queryById(Long kbId);

        /**
         * 查询知识库主表列表
         */
        TableDataInfo<KbBaseVo> queryPageList(KbBaseBo bo, PageQuery pageQuery);

    /**
     * 查询知识库主表列表
     */
    List<KbBaseVo> queryList(KbBaseBo bo);

    /**
     * 新增知识库主表
     */
    Boolean insertByBo(KbBaseBo bo);

    /**
     * 修改知识库主表
     */
    Boolean updateByBo(KbBaseBo bo);

    /**
     * 校验并批量删除知识库主表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
