package com.yuan.workflow.domain.vo.detail;

import com.yuan.workflow.domain.vo.*;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WfApprovalDetailVO {
    /** 实例基础信息 */
    private WfInstanceVo instance;

    /** 业务引用（wf_biz_ref） */
    private WfBizRefVo biz;

    /** 当前操作上下文（待办页会有；申请详情页可能为空） */
    private WfTaskVo current;

    /** 审批进度：节点轨迹（按 orderNo 升序） */
    private List<WfTimelineEventVo> timeline;

    private List<WfNodeInstanceVo> stepNodes;

    private LfGraph lfGraph;

    private List<List<LfNode>> layers;

    /** 前端按钮权限 */
    private OpsVO ops;
}
