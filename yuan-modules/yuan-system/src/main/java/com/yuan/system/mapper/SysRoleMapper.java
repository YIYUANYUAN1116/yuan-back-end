package com.yuan.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.annotation.DataColumn;
import com.yuan.annotation.DataPermission;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysRole;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色Mapper接口
 *
 
 * @date Sun Dec 07 17:25:44 CST 2025
 */
@Mapper
public interface SysRoleMapper extends BaseMapperPlus<SysRole, SysRoleVo> {

    List<SysRoleVo> selectRolesByUserId(Long userId);

    Long[] selectRoleIdsByUserId(Long userId);


    Page<SysUserVo> selectAllocatedUserList(@Param("page") Page<SysUser> page,@Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper,@Param("roleId") Long roleId);

    List<SysRoleVo> selectRolePermissionByRoleUser(Long userId);

    SysRole selectRolesByRoleKey(@Param("roleKey") String roleKey,@Param("tenantId")String tenantId);

    /**
     * 根据条件分页查询角色数据
     *
     * @param queryWrapper 查询条件
     * @return 角色数据集合信息
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "d.dept_id")
    })
    List<SysRoleVo> selectRoleList(@Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

    List<SysRoleVo>  selectRolePermissionByPostUser(Long userId);
}
