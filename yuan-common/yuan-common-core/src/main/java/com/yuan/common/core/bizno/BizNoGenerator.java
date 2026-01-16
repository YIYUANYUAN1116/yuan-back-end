package com.yuan.common.core.bizno;

public interface BizNoGenerator {
    /**
     * 生成业务单号
     *
     * @param bizPrefix 业务前缀，如 OA / EXP
     * @return 业务单号，如 OA20260116000001
     */
    String generate(BizNoPrefixEnum bizPrefix);
}
