package com.yuan.common.oss.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NameSpace {
    TEMP("temp"),//临时文件，如只上传但没绑定业务
    FORMAL("formal"),//业务文件，业务已确认的文件，如头像，
    PUBLIC("public");//公开文件,如：首页图片等
    private final String space;
}
