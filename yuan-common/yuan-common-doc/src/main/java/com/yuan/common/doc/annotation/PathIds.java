package com.yuan.common.doc.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Parameter(
        array = @ArraySchema(
                schema = @Schema(type = "string")
        )
)
public @interface PathIds {

    /**
     * 参数描述（语义）
     */
    String desc() default "";
}
