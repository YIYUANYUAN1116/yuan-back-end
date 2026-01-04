package com.yuan.workflow.component.aop;

import com.yuan.workflow.annotation.FillWfCmd;
import com.yuan.workflow.api.cmd.WorkflowCmd;
import com.yuan.workflow.component.WfCmdFiller;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

@Aspect
@RequiredArgsConstructor
@Configuration
public class WfCmdFillAspect {
    private final WfCmdFiller wfCmdFiller;

    @Around("@within(com.yuan.workflow.annotation.FillWfCmd) || @annotation(com.yuan.workflow.annotation.FillWfCmd)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        // 允许方法覆盖类上的设置
        FillWfCmd ann = AnnotatedElementUtils.findMergedAnnotation(method, FillWfCmd.class);
        if (ann == null) {
            ann = AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), FillWfCmd.class);
        }
        if (ann != null && !ann.value()) {
            return pjp.proceed();
        }

        Object[] args = pjp.getArgs();
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof WorkflowCmd cmd) {
                    wfCmdFiller.fill(cmd);
                }
            }
        }
        return pjp.proceed();
    }
}
