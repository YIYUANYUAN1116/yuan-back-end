package com.yuan.system.service;

import com.yuan.system.domain.vo.SysUserPostVo;
import com.yuan.system.domain.bo.SysUserPostBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * post-userService接口
 *
 * @author ageerle
 * @date Mon Dec 22 15:05:48 CST 2025
 */
public interface SysUserPostService {

    /**
     * 查询post-user
     */
        SysUserPostVo queryById(Long postId);

        /**
         * 查询post-user列表
         */
        TableDataInfo<SysUserPostVo> queryPageList(SysUserPostBo bo, PageQuery pageQuery);

    /**
     * 查询post-user列表
     */
    List<SysUserPostVo> queryList(SysUserPostBo bo);

    /**
     * 新增post-user
     */
    Boolean insertByBo(SysUserPostBo bo);

    /**
     * 修改post-user
     */
    Boolean updateByBo(SysUserPostBo bo);

    /**
     * 校验并批量删除post-user信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
