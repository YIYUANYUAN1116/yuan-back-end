package com.yuan.common.core.service;

public class BaseContext {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setCurrentToken(String token){
        threadLocal.set(token);
    }

    public static String getCurrentToken(){
        return threadLocal.get();
    }
}