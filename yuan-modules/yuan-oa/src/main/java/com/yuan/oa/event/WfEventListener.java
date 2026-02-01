package com.yuan.oa.event;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.common.core.constant.ApplyConstants;
import com.yuan.common.core.enums.apply.ApplyStatus;
import com.yuan.oa.domain.OaLeaveApply;
import com.yuan.oa.mapper.OaLeaveApplyMapper;
import com.yuan.workflow.enums.WfEndReason;
import com.yuan.workflow.event.WfEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
public class WfEventListener {
    private final OaLeaveApplyMapper oaLeaveApplyMapper;

    @Async
    @EventListener
    public void listen(WfEvent wfEvent) {
        String bizType = wfEvent.getBizType();
        if (Objects.equals(bizType, ApplyConstants.OA_LEAVE)) {
            log.info("[OA-Leave][WfEventListener] received event. bizId={},endReason={}", wfEvent.getBizId(), wfEvent.getReason());
            handlerOaLeaveApply(wfEvent);
        }
    }

    public void handlerOaLeaveApply(WfEvent wfEvent) {
        Long bizId = wfEvent.getBizId();
        WfEndReason endReason = wfEvent.getEndReason();
        ApplyStatus status = null;
        if (Objects.equals(endReason, WfEndReason.APPROVED)) {
            status = ApplyStatus.APPROVED;
        } else if (Objects.equals(endReason, WfEndReason.REJECTED)) {
            status = ApplyStatus.REJECTED;
        } else if (Objects.equals(endReason, WfEndReason.WITHDRAWN)) {
            status = ApplyStatus.CANCELED;
        }

        if (status != null) {
            oaLeaveApplyMapper.update(Wrappers.<OaLeaveApply>lambdaUpdate()
                    .eq(OaLeaveApply::getId, bizId)
                    .set(OaLeaveApply::getStatus, status));
        }

        //做一些其它业务逻辑
    }
}
