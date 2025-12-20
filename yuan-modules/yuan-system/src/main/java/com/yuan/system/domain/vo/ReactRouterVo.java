package com.yuan.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReactRouterVo {
    private String name;
    private String path;
    private String icon;
    private String component;
    private Boolean layout;
    private Boolean hideInMenu;
    //权限
    private String access;
    private List<ReactRouterVo> routes; // 子菜单
}
