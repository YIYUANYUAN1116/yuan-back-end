package com.yuan.common.core.exception.oa;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OaErrorCode {

    CANNOT_EDIT_NON_DRAFT("CANNOT_EDIT_NON_DRAFT","当前申请不是草稿状态，无法编辑"),
    CANNOT_DELETE_NON_DRAFT("CANNOT_DELETE_NON_DRAFT","当前申请不是草稿状态，无法删除");

    private final String code;
    private final String defaultMessage;
}
