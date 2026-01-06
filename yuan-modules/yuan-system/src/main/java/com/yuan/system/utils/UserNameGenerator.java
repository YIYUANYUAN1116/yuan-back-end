package com.yuan.system.utils;

public class UserNameGenerator {

    /**
     * 根据雪花ID生成用户账号
     * 示例：U482931
     */
    public static String generateUserName(long snowflakeId) {
        String tail = String.valueOf(Math.abs(snowflakeId) % 1_000_000);
        return "U" + String.format("%06d", Long.parseLong(tail));
    }
}
