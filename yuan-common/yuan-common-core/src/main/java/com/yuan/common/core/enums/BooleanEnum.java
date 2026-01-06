package com.yuan.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooleanEnum {
    /**
     * ture
     */
    TURE(1),

    /**
     * false
     */
    FALSE(0);

    private final Integer value;
}
