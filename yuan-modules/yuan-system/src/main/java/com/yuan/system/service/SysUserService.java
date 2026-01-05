package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysUserVo;

import java.util.Collection;
import java.util.List;

/**
 * 用户Service接口
 *
 
 * @date Sun Dec 07 17:25:38 CST 2025
 */
public interface SysUserService {

    /**
     * 查询用户
     */
        SysUserVo queryById(Long userId);

        /**
         * 查询用户列表
         */
        TableDataInfo<SysUserVo> queryPageList(SysUserBo bo, PageQuery pageQuery);

    /**
     * 查询用户列表
     */
    List<SysUserVo> queryList(SysUserBo bo);

    /**
     * 新增用户
     */
    Boolean insertByBo(SysUserBo bo);

    /**
     * 修改用户
     */
    Boolean updateByBo(SysUserBo bo);

    /**
     * 校验并批量删除用户信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    boolean checkNickNameUnique(SysUserBo user);

    boolean checkPhoneUnique(SysUserBo user);

    boolean checkEmailUnique(SysUserBo user);

    SysUserVo selectUserById(Long userId);

    String selectUserRoleGroup(Long userId);

    String selectUserPostGroup(Long userId);

    int updateUserProfile(SysUserBo user);

    int resetUserPwd(Long userId, String hashpw);
}
