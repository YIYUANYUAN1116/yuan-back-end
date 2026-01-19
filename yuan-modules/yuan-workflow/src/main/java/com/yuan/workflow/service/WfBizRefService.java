package com.yuan.workflow.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.cmd.StartProcessCmd;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.bo.WfBizRefBo;
import com.yuan.workflow.domain.vo.WfBizRefVo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * wfrefService接口
 *
 * @author yuan
 * @date Mon Dec 29 20:51:33 CST 2025
 */
public interface WfBizRefService {

    /**
     * 查询wfref
     */
        WfBizRefVo queryById(Long id);

        /**
         * 查询wfref列表
         */
        TableDataInfo<WfBizRefVo> queryPageList(WfBizRefBo bo, PageQuery pageQuery);

    /**
     * 查询wfref列表
     */
    List<WfBizRefVo> queryList(WfBizRefBo bo);

    /**
     * 新增wfref
     */
    Boolean insertByBo(WfBizRefBo bo);

    /**
     * 修改wfref
     */
    Boolean updateByBo(WfBizRefBo bo);

    /**
     * 校验并批量删除wfref信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void bindWfBizRef(StartProcessCmd cmd, Long id);

    List<WfBizRef> listByInstanceIds(Set<Long> instanceIds);

    WfBizRefVo selectOneVoByBizNo(String bizNo);
}
