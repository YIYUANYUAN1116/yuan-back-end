package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysBizNoSeqBo;
import com.yuan.system.domain.vo.SysBizNoSeqVo;

import java.util.Collection;
import java.util.List;

/**
 * 业务序列表Service接口
 *
 * @author yuan
 * @date Fri Jan 16 16:53:43 CST 2026
 */
public interface SysBizNoSeqService {

    /**
     * 查询业务序列表
     */
        SysBizNoSeqVo queryById(Long id);

        /**
         * 查询业务序列表列表
         */
        TableDataInfo<SysBizNoSeqVo> queryPageList(SysBizNoSeqBo bo, PageQuery pageQuery);

    /**
     * 查询业务序列表列表
     */
    List<SysBizNoSeqVo> queryList(SysBizNoSeqBo bo);

    /**
     * 新增业务序列表
     */
    Boolean insertByBo(SysBizNoSeqBo bo);

    /**
     * 修改业务序列表
     */
    Boolean updateByBo(SysBizNoSeqBo bo);

    /**
     * 校验并批量删除业务序列表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
