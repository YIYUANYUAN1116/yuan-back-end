package com.yuan.common.core.utils;

import com.yuan.common.core.constant.AiBizTypeConstants;

import java.util.UUID;

public class TraceIdUtil {

    public static String newTraceId() {
        return AiBizTypeConstants.CHAT + System.currentTimeMillis() + "-" +
                UUID.randomUUID().toString().substring(0,6);
    }

    //前缀
    public static String newTraceIdPrefix(String prefix) {
        return prefix+"-" + System.currentTimeMillis() + "-" +
                UUID.randomUUID().toString().substring(0,6);
    }

}