package com.yuan.common.core.enums.apply;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyStatus {
    //DRAFT/APPROVING/APPROVED/REJECTED/CANCELED
    DRAFT("DRAFT"),
    APPROVING("APPROVING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    CANCELED("CANCELED");

    private final String code;
}
