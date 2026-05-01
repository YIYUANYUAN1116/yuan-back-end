package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbBaseAuthVo;
import com.yuan.ai.domain.bo.KbBaseAuthBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库授权表Service接口
 *
 * @author yuan
 * @date Fri May 01 16:08:26 CST 2026
 */
public interface KbBaseAuthService {

    /**
     * 查询知识库授权表
     */
        KbBaseAuthVo queryById(Long authId);

        /**
         * 查询知识库授权表列表
         */
        TableDataInfo<KbBaseAuthVo> queryPageList(KbBaseAuthBo bo, PageQuery pageQuery);

    /**
     * 查询知识库授权表列表
     */
    List<KbBaseAuthVo> queryList(KbBaseAuthBo bo);

    /**
     * 新增知识库授权表
     */
    Boolean insertByBo(KbBaseAuthBo bo);

    /**
     * 修改知识库授权表
     */
    Boolean updateByBo(KbBaseAuthBo bo);

    /**
     * 校验并批量删除知识库授权表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
