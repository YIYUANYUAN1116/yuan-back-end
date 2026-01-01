package com.yuan.admin.controller;

import com.yuan.common.core.domain.R;
import com.yuan.common.core.domain.model.LoginBody;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.system.domain.vo.LoginVo;
import com.yuan.system.service.SysAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController",description = "认证接口")
public class AuthController {

    private final SysAuthService authService;
//    private final SysRegisterService registerService;

    /**
     * 登录方法
     *
     * @param body 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @Operation(summary = "登录",operationId = "login")
    public R<LoginVo> login(@Validated @RequestBody LoginBody body) {
        LoginVo loginVo = new LoginVo();
        // 生成令牌
        String token = authService.login(
                body.getUsername(), body.getPassword(),
                body.getCode(), body.getUuid());
        loginVo.setToken(token);
        // 兼容后台管理登录
        loginVo.setAccess_token(token);
        loginVo.setUserInfo(LoginHelper.getLoginUser());
        return R.ok(loginVo);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @Operation(summary = "退出登录",operationId = "logout")
    public R<Void> logout() {
        authService.logout();
        return R.ok("退出成功");
    }

}
