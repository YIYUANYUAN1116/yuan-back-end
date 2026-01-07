package com.yuan.system.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.yuan.common.core.constant.Constants;
import com.yuan.common.core.constant.GlobalConstants;
import com.yuan.common.core.domain.dto.RoleDTO;
import com.yuan.common.core.domain.model.LoginUser;
import com.yuan.common.core.enums.DeviceType;
import com.yuan.common.core.enums.LoginType;
import com.yuan.common.core.enums.UserStatus;
import com.yuan.common.core.exception.user.UserException;
import com.yuan.common.core.utils.MessageUtils;
import com.yuan.common.core.utils.ServletUtils;
import com.yuan.common.core.utils.SpringUtils;
import com.yuan.common.log.event.LogininforEvent;
import com.yuan.common.redis.utils.RedisUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
@Service
public class SysAuthService {
    private final SysUserMapper userMapper;
    private final ISysPermissionService permissionService;

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;
    @Value("${user.password.lockTime}")
    private Integer lockTime;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        SysUserVo user = loadUserByUsername(username);
        checkLogin(LoginType.PASSWORD, user.getUserId(),user.getTenantId(), username, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);

        recordLogininfor(loginUser.getTenantId(),user.getUserId() ,username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId());
        return StpUtil.getTokenValue();
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(ServletUtils.getClientIP());
        sysUser.setLoginDate(LocalDateTime.now());
        sysUser.setUpdateBy(userId);
        userMapper.updateById(sysUser);
    }

    private SysUserVo loadUserByUsername(String username) {
        SysUserVo user = userMapper.selectUserByUsernameByLogin(username);
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
//        if (TenantHelper.isEnable()) {
//            return userMapper.selectTenantUserByUserName(username, tenantId);
//        }
        return user;
    }

    private LoginUser buildLoginUser(SysUserVo user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setTenantId(user.getTenantId());
        loginUser.setUserId(user.getUserId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setNickName(user.getNickName());
        loginUser.setUsername(user.getUserName());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setDeptName(user.getDeptName());
        loginUser.setUserType(user.getUserType());
        loginUser.setKroleGroupIds(user.getKroleGroupIds());
        loginUser.setKroleGroupType(user.getKroleGroupType());
        loginUser.setMenuPermission(permissionService.getMenuPermission(user.getUserId()));
        loginUser.setRolePermission(permissionService.getRolePermission(user.getUserId()));
        List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
        loginUser.setRoles(roles);
        return loginUser;
    }

    private void checkLogin(LoginType loginType,Long userId, String tenantId, String username, Supplier<Boolean> supplier) {
        String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        Integer errorNumber = RedisUtils.getCacheObject(errorKey);
        // 锁定时间内登录 则踢出
        if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(maxRetryCount)) {
            recordLogininfor(tenantId,userId,username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 是否第一次
            errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
            // 达到规定错误次数 则锁定登录
            if (errorNumber.equals(maxRetryCount)) {
                RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
                recordLogininfor(tenantId,userId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数 则递增
                RedisUtils.setCacheObject(errorKey, errorNumber);
                recordLogininfor(tenantId,userId, username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }

    /**
     * 记录登录信息
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    private void recordLogininfor(String tenantId,Long userId, String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        logininforEvent.setUserId(userId);
        SpringUtils.context().publishEvent(logininforEvent);
    }

    public void logout() {
        try {
            LoginUser loginUser = LoginHelper.getLoginUser();
//            if (TenantHelper.isEnable() && LoginHelper.isSuperAdmin()) {
//                // 超级管理员 登出清除动态租户
//                TenantHelper.clearDynamic();
//            }
            StpUtil.logout();
            if (loginUser != null) {
                recordLogininfor(loginUser.getTenantId(),loginUser.getUserId() ,loginUser.getUsername(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
            }
        } catch (NotLoginException ignored) {
        }
    }
}
