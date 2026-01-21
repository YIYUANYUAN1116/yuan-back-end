package com.yuan.workflow.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class OpsVO {
    private boolean canApprove;
    private boolean canReject;
    private boolean canRollback;
    private boolean canTransfer;
    private boolean canWithdraw;

    /** 退回可选目标（后端算好，避免前端猜规则） */
    private List<RollbackTargetVO> rollbackTargets;

    /** 转交候选（可选：也可以前端弹窗远程搜索） */
    private List<UserOptionVO> transferUsers;
}