package com.yuan.common.core.bizno;

import com.yuan.common.core.exception.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum BizNoPrefixEnum {

    OA("OA", "OA-请假/办公"),
    SYS("SYS", "系统管理"),
    EXP("EXP", "报销单"),
    CON("CON", "合同"),
    PUR("PUR", "采购单"),
    WF("WF", "流程实例");

    private final String prefix;
    private final String desc;

    public static BizNoPrefixEnum findByPrefix(String prefix) {
        for (BizNoPrefixEnum p : values()) {
            if (p.getPrefix().equals(prefix)) {
                return p;
            }
        }
        log.warn("[BizNoPrefixEnum][findByPrefix] invalid prefix [{}]", prefix);
        throw new BaseException("无效前缀");
    }
}
