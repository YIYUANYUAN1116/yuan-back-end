package com.yuan.system.domain.vo;

import com.yuan.common.core.domain.model.LoginUser;
import lombok.Data;

@Data
public class LoginVo {
    private String token;
    // 兼容新版后台管理系统
    private String access_token;
    private LoginUser userInfo;
}