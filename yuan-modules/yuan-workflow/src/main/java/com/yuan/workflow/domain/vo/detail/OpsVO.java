package com.yuan.workflow.domain.vo.detail;

import lombok.Data;

@Data
public class OpsVO {
    private boolean canApprove;
    private boolean canReject;
    private boolean canRollback;
    private boolean canTransfer;
    private boolean canWithdraw;
    private boolean canAddSign;
}