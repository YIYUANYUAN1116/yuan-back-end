package com.yuan.system.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class SelectRolesVo {
    private List<SysRoleVo> roles;
    private Long[] checkedKeys;
}
