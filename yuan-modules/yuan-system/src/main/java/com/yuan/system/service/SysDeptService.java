package com.yuan.system.service;


import cn.hutool.core.lang.tree.Tree;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysDeptBo;
import com.yuan.system.domain.vo.SysDeptVo;

import java.util.Collection;
import java.util.List;

/**
 * 部门Service接口
 *

 * @date Wed Dec 10 17:08:31 CST 2025
 */
public interface SysDeptService {

    /**
     * 查询部门
     */
        SysDeptVo queryById(Long deptId);

        /**
         * 查询部门列表
         */
        TableDataInfo<SysDeptVo> queryPageList(SysDeptBo bo, PageQuery pageQuery);

    /**
     * 查询部门列表
     */
    List<SysDeptVo> queryList(SysDeptBo bo);

    /**
     * 新增部门
     */
    Boolean insertByBo(SysDeptBo bo);

    /**
     * 修改部门
     */
    Boolean updateByBo(SysDeptBo bo);

    /**
     * 校验并批量删除部门信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<SysDeptVo> listTree(SysDeptBo bo);

    List<SysDeptVo> selectDeptList(SysDeptBo bo, Long userId);

    List<Tree<Long>> buildDeptTreeSelect(List<SysDeptVo> deptVos);

//    TableDataInfo<SysUserVo> selectAllocatedUserList(SysUserBo bo, PageQuery pageQuery);
//
//    TableDataInfo<SysUserVo> selectUnallocatedUserList(SysUserBo bo, PageQuery pageQuery);
//
//    Boolean cancelUserAll(Long deptId, Long[] userIds);
//
//    Boolean selectUserAll(Long deptId, Long[] userIds);

    Boolean checkDeptNameUnique(SysDeptBo bo);
}
