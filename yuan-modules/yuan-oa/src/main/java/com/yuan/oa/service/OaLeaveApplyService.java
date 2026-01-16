package com.yuan.oa.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.oa.domain.bo.OaLeaveApplyBo;
import com.yuan.oa.domain.vo.OaLeaveApplyVo;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * oa_leaveService接口
 *
 * @author yuan
 * @date Fri Jan 16 13:46:51 CST 2026
 */
public interface OaLeaveApplyService {

    /**
     * 查询oa_leave
     */
        OaLeaveApplyVo queryById(Long id);

        /**
         * 查询oa_leave列表
         */
        TableDataInfo<OaLeaveApplyVo> queryPageList(OaLeaveApplyBo bo, PageQuery pageQuery);

    /**
     * 查询oa_leave列表
     */
    List<OaLeaveApplyVo> queryList(OaLeaveApplyBo bo);

    /**
     * 新增oa_leave
     */
    Boolean insertByBo(OaLeaveApplyBo bo);

    /**
     * 修改oa_leave
     */
    Boolean updateByBo(OaLeaveApplyBo bo);

    /**
     * 校验并批量删除oa_leave信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    OaLeaveApplyVo queryByBizNo(@NotNull(message = "主键不能为空") String bizNo);
}
