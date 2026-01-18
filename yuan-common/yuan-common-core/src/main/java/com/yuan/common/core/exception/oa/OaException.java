package com.yuan.common.core.exception.oa;

import lombok.Getter;

@Getter
public class OaException extends RuntimeException{
    private final OaErrorCode errorCode;

    public OaException(OaErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    public OaException(OaErrorCode errorCode, Throwable cause) {
        super(errorCode.getDefaultMessage(), cause);
        this.errorCode = errorCode;
    }
}
