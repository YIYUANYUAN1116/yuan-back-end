package com.yuan.system.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.common.core.constant.Constants;
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
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginService {
    private final SysUserMapper userMapper;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String tenantId, String username, String password, String code, String uuid) {
        SysUserVo user = loadUserByUsername(tenantId, username);
        checkLogin(LoginType.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);

        recordLogininfor(loginUser.getTenantId(), username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
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

    private SysUserVo loadUserByUsername(String tenantId, String username) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserName, SysUser::getStatus)
//                .eq(TenantHelper.isEnable(), SysUser::getTenantId, tenantId)
                .eq(SysUser::getUserName, username));
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
        return userMapper.selectUserByUserName(username);
    }

    private LoginUser buildLoginUser(SysUserVo user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setTenantId(user.getTenantId());
        loginUser.setUserId(user.getUserId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setUsername(user.getUserName());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setUserType(user.getUserType());
        loginUser.setKroleGroupIds(user.getKroleGroupIds());
        loginUser.setKroleGroupType(user.getKroleGroupType());
        List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
        loginUser.setRoles(roles);
        return loginUser;
    }

    private void checkLogin(LoginType loginType, String tenantId, String username, Supplier<Boolean> supplier) {

    }

    /**
     * 记录登录信息
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    private void recordLogininfor(String tenantId, String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }
}
