package com.yuan.ai.domain.model;

import lombok.Data;

@Data
public class ChatMsg {
    private String role;    // system/user/assistant
    private String content;
}