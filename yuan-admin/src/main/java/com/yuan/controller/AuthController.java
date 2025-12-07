package com.yuan.controller;

import com.yuan.common.core.constant.Constants;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.domain.model.LoginBody;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.system.domain.vo.LoginVo;
import com.yuan.system.service.SysLoginService;
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
@Tag(name = "认证接口")
public class AuthController {

    private final SysLoginService loginService;
//    private final SysRegisterService registerService;

    /**
     * 登录方法
     *
     * @param body 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @Operation(summary = "登录")
    public R<LoginVo> login(@Validated @RequestBody LoginBody body) {
        body.setTenantId(Constants.TENANT_ID);
        LoginVo loginVo = new LoginVo();
        // 生成令牌
        String token = loginService.login(
                body.getTenantId(),
                body.getUsername(), body.getPassword(),
                body.getCode(), body.getUuid());
        loginVo.setToken(token);
        // 兼容后台管理登录
        loginVo.setAccess_token(token);
        loginVo.setUserInfo(LoginHelper.getLoginUser());
        return R.ok(loginVo);
    }

}
