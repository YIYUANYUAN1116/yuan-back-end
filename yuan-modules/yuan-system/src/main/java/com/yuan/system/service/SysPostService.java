package com.yuan.system.service;

import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.system.domain.bo.SysPostBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * postService接口
 *
 * @author ageerle
 * @date Mon Dec 22 15:05:40 CST 2025
 */
public interface SysPostService {

    /**
     * 查询post
     */
        SysPostVo queryById(Long postId);

        /**
         * 查询post列表
         */
        TableDataInfo<SysPostVo> queryPageList(SysPostBo bo, PageQuery pageQuery);

    /**
     * 查询post列表
     */
    List<SysPostVo> queryList(SysPostBo bo);

    /**
     * 新增post
     */
    Boolean insertByBo(SysPostBo bo);

    /**
     * 修改post
     */
    Boolean updateByBo(SysPostBo bo);

    /**
     * 校验并批量删除post信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
