package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysBizNoSeq;
import com.yuan.system.domain.vo.SysBizNoSeqVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 业务序列表Mapper接口
 *
 * @author yuan
 * @date Fri Jan 16 16:53:43 CST 2026
 */
@Mapper
public interface SysBizNoSeqMapper extends BaseMapperPlus<SysBizNoSeq, SysBizNoSeqVo> {


    SysBizNoSeq selectForUpdate(@Param("bizPrefix") String bizPrefix, @Param("bizDate") String bizDate);
}
