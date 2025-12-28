package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysRoleDeptBo;
import com.yuan.system.domain.vo.SysRoleDeptVo;

import java.util.Collection;
import java.util.List;

/**
 * 部门角色Service接口
 *
 
 * @date Wed Dec 10 17:21:37 CST 2025
 */
public interface SysRoleDeptService {

    /**
     * 查询部门角色
     */
        SysRoleDeptVo queryById(Long deptId);

        /**
         * 查询部门角色列表
         */
        TableDataInfo<SysRoleDeptVo> queryPageList(SysRoleDeptBo bo, PageQuery pageQuery);

    /**
     * 查询部门角色列表
     */
    List<SysRoleDeptVo> queryList(SysRoleDeptBo bo);

    /**
     * 新增部门角色
     */
    Boolean insertByBo(SysRoleDeptBo bo);

    /**
     * 修改部门角色
     */
    Boolean updateByBo(SysRoleDeptBo bo);

    /**
     * 校验并批量删除部门角色信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
