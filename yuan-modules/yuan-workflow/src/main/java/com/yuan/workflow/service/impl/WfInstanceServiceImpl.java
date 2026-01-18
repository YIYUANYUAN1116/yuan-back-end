package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StreamUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.json.utils.JsonUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.api.UserQueryApi;
import com.yuan.workflow.cmd.StartProcessCmd;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.bo.WfInstanceBo;
import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.domain.vo.WfInstanceVo;
import com.yuan.workflow.domain.vo.WorkItemRowVO;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.service.WfBizRefService;
import com.yuan.workflow.service.WfInstanceService;
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * wfiService业务层处理
 *
 * @date Sun Dec 28 11:26:34 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfInstanceServiceImpl implements WfInstanceService {

    private final WfInstanceMapper baseMapper;
    private final WfBizRefService bizRefService;
    private final WfNodeInstanceService nodeInstanceService;
    private final UserQueryApi userQueryApi;
    private final WfTaskService wfTaskService;

    /**
     * 查询wfi
     */
    @Override
    public WfInstanceVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询wfi列表
     */
    @Override
    public TableDataInfo<WfInstanceVo> queryPageList(WfInstanceBo bo, PageQuery pageQuery) {
        QueryWrapper<WfInstance> lqw = buildQueryWrapper(bo);
        Page<WfInstanceVo> result = baseMapper.selectWfInstanceVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询wfi列表
     */
    @Override
    public List<WfInstanceVo> queryList(WfInstanceBo bo) {
        QueryWrapper<WfInstance> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private QueryWrapper<WfInstance> buildQueryWrapper(WfInstanceBo bo) {
        QueryWrapper<WfInstance> lqw = Wrappers.query();
                lqw.eq(bo.getId() != null, "wi.id", bo.getId());
                lqw.eq(bo.getTenantId() != null, "wi.tenant_id", bo.getTenantId());
                lqw.eq(bo.getDefinitionId() != null, "wi.definition_id", bo.getDefinitionId());
                lqw.like(StringUtils.isNotBlank(bo.getDefinitionKey()), "wi.definition_key", bo.getDefinitionKey());
                lqw.eq(bo.getDefinitionVersion() != null, "wi.version", bo.getDefinitionVersion());
                lqw.eq(StringUtils.isNotBlank(bo.getStatus()), "wi.status", bo.getStatus());
                lqw.eq(bo.getStarterId() != null, "wi.starter_id", bo.getStarterId());
                lqw.like(StringUtils.isNotBlank(bo.getStarterName()), "wi.starter_name", bo.getStarterName());
                lqw.like(StringUtils.isNotBlank(bo.getStarterDeptName()), "wi.starter_dept_name", bo.getStarterDeptName());
                lqw.eq(bo.getStartTime() != null, "wi.starter_time", bo.getStartTime());
                lqw.eq(bo.getEndTime() != null, "wi.end_time", bo.getEndTime());
                lqw.like(StringUtils.isNotBlank(bo.getBizNo()), "wr.biz_no", bo.getBizNo());
                lqw.eq(StringUtils.isNotBlank(bo.getBizType()), "wr.biz_type", bo.getBizType());
                lqw.like(StringUtils.isNotBlank(bo.getDefinitionName()), "wi.definition_name", bo.getDefinitionName());
        return lqw;
    }

    private LambdaQueryWrapper<WfInstance> buildLambdaQueryWrapper(WfInstanceBo bo) {
        LambdaQueryWrapper<WfInstance> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, WfInstance::getId, bo.getId());
        lqw.eq(bo.getTenantId() != null, WfInstance::getTenantId, bo.getTenantId());
        lqw.eq(bo.getDefinitionId() != null, WfInstance::getDefinitionId, bo.getDefinitionId());
        lqw.eq(StringUtils.isNotBlank(bo.getDefinitionKey()), WfInstance::getDefinitionKey, bo.getDefinitionKey());
        lqw.eq(bo.getDefinitionVersion() != null, WfInstance::getDefinitionVersion, bo.getDefinitionVersion());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), WfInstance::getStatus, bo.getStatus());
        lqw.eq(bo.getStarterId() != null, WfInstance::getStarterId, bo.getStarterId());
        lqw.eq(bo.getStartTime() != null, WfInstance::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, WfInstance::getEndTime, bo.getEndTime());
        return lqw;
    }

    /**
     * 新增wfi
     */
    @Override
    public Boolean insertByBo(WfInstanceBo bo) {
        WfInstance add = MapstructUtils.convert(bo, WfInstance.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wfi
     */
    @Override
    public Boolean updateByBo(WfInstanceBo bo) {
        WfInstance update = MapstructUtils.convert(bo, WfInstance.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfInstance entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wfi
     */
    @Override
    @Transactional
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        nodeInstanceService.deleteByInstanceIds(ids);
        wfTaskService.deleteByInstanceIds(ids);

        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public WfInstance createInstance(StartProcessCmd cmd, WfDefinition def) {
        WfInstance instance = new WfInstance();
        instance.setTenantId(cmd.getTenantId());
        instance.setDefinitionId(def.getId());
        instance.setDefinitionKey(def.getDefinitionKey());
        instance.setDefinitionVersion(def.getVersion());
        instance.setStatus(InstanceStatus.RUNNING);
        instance.setStarterId(cmd.getStarterId());
        instance.setStarterName(cmd.getStarterName());
        instance.setLastOperatorId(cmd.getOperatorId());
        instance.setLastOperatorName(cmd.getOperatorName());
        instance.setStarterDeptId(cmd.getStarterDeptId());
        instance.setStarterDeptName(cmd.getStarterDeptName());
        instance.setDefinitionName(def.getDefinitionName());
        instance.setVariables(JsonUtils.toJsonString(cmd.getVariables()));
        baseMapper.insert(instance);
        return instance;
    }

    @Override
    public TableDataInfo<WorkItemRowVO> myApply(WfInstanceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WfInstance> lqw = buildLambdaQueryWrapper(bo);
        if (!LoginHelper.isSuperAdmin()){
            lqw.eq(WfInstance::getStarterId, LoginHelper.getUserId());
        }
        Page<WfInstance> wfInstancePage = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(enrichFromInstancePage(wfInstancePage));
    }

    private Page<WorkItemRowVO> enrichFromInstancePage(Page<WfInstance> insPage) {
        List<WfInstance> instances = insPage.getRecords();
        if (instances.isEmpty()) {
            return new Page<>(insPage.getCurrent(), insPage.getSize(), insPage.getTotal());
        }

        Set<Long> instanceIds = instances.stream().map(WfInstance::getId).collect(Collectors.toSet());

        List<WfBizRef> bizRefs = bizRefService.listByInstanceIds(instanceIds);
        Map<Long, WfBizRef> bizMap = StreamUtils.toMap(bizRefs, WfBizRef::getInstanceId);

        // 当前节点（WAIT）
        List<WfNodeInstance> waitNodes = nodeInstanceService.listWaitNodesByInstanceIds(instanceIds);
        // 如果一个实例可能多个 WAIT，这里按 orderNo 最大取一个
        Map<Long, WfNodeInstance> currentNodeMap = waitNodes.stream()
                .collect(Collectors.toMap(
                        WfNodeInstance::getInstanceId,
                        Function.identity(),
                        (a, b) -> (a.getOrderNo() >= b.getOrderNo()) ? a : b
                ));

        Set<Long> userIds = instances.stream().map(WfInstance::getStarterId).collect(Collectors.toSet());
        Map<Long, String> userNameMap = userQueryApi.getUserNameMap(userIds);

        List<WorkItemRowVO> rows = instances.stream().map(ins -> {
            WorkItemRowVO vo = new WorkItemRowVO();
            vo.setInstanceId(ins.getId());
            vo.setInstanceStatus(ins.getStatus());
            vo.setInstanceStartTime(ins.getStartTime());
            vo.setInstanceEndTime(ins.getEndTime());
            vo.setInstanceEndReason(ins.getEndReason());
            vo.setInstanceEndComment(ins.getEndComment());

            vo.setStarterId(ins.getStarterId());
            vo.setStarterName(userNameMap.get(ins.getStarterId()));

            WfBizRef br = bizMap.get(ins.getId());
            if (br != null) {
                vo.setBizType(br.getBizType());
                vo.setBizId(br.getBizId());
                vo.setBizNo(br.getBizNo());
            }

            WfNodeInstance cur = currentNodeMap.get(ins.getId());
            if (cur != null) {
                vo.setNodeInstanceId(cur.getId());
                vo.setNodeKey(cur.getNodeKey());
                vo.setNodeName(cur.getNodeName());
            } else {
                // 已结束实例可能无 WAIT 节点
                vo.setNodeName("已结束");
            }
            return vo;
        }).toList();

        Page<WorkItemRowVO> voPage = new Page<>(insPage.getCurrent(), insPage.getSize(), insPage.getTotal());
        voPage.setRecords(rows);
        return voPage;
    }

}
