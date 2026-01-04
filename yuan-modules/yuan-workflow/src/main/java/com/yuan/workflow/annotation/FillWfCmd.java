package com.yuan.workflow.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解自动注入一些上下文信息
 * @see com.yuan.workflow.component.WfCmdFiller
 * @see com.yuan.workflow.component.aop.WfCmdFillAspect
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface FillWfCmd {
    /**
     * 是否开启填充（方便个别方法跳过）
     */
    boolean value() default true;
}
