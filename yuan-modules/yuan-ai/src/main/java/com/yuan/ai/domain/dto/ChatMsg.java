package com.yuan.ai.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatMsg {
    @NotBlank
    private String role; // system/user/assistant
    @NotBlank
    private String content;
}