package com.yuan.system.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SysUserDTO {
    private Long userId;
    private String openId;
    private String userGrade;
    private BigDecimal userBalance;
    private String tenantId;
    private Long deptId;
    private String userName;
    private String nickName;
    private String userType;
    private String email;
    private String phonenumber;
    private String sex;
    private String avatar;
    private String wxAvatar;
}
