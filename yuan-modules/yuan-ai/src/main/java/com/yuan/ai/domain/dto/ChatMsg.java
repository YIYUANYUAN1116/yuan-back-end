package com.yuan.ai.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg {
    @NotBlank
    private String role; // system/user/assistant
    @NotBlank
    private String content;
}