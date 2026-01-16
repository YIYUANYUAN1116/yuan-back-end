package com.yuan.system.service.api;

import com.yuan.common.core.bizno.BizNoGenerator;
import com.yuan.common.core.bizno.BizNoPrefixEnum;
import com.yuan.system.domain.SysBizNoSeq;
import com.yuan.system.mapper.SysBizNoSeqMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BizNoGeneratorImpl implements BizNoGenerator {
    private final SysBizNoSeqMapper bizNoSeqMapper;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Transactional
    @Override
    public String generate(BizNoPrefixEnum bizPrefix) {

        String date = LocalDate.now().format(DATE_FMT);

        // 1. 查当前记录（for update，防并发）
        SysBizNoSeq seq = bizNoSeqMapper.selectForUpdate(bizPrefix.getPrefix(), date);

        int nextNo;
        if (seq == null) {
            // 2. 当天第一条
            nextNo = 1;
            seq = new SysBizNoSeq();
            seq.setBizPrefix(bizPrefix.getPrefix());
            seq.setBizDate(date);
            seq.setCurrentNo(nextNo);
            bizNoSeqMapper.insert(seq);
        } else {
            // 3. 自增
            nextNo = seq.getCurrentNo() + 1;
            seq.setCurrentNo(nextNo);
            bizNoSeqMapper.updateById(seq);
        }

        // 4. 拼 bizNo
        return bizPrefix + date + String.format("%06d", nextNo);
    }
}
