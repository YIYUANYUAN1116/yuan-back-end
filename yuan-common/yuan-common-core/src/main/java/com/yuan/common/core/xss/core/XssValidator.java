package com.yuan.common.core.xss.core;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HtmlUtil;
import com.yuan.common.core.xss.annotation.Xss;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class XssValidator implements ConstraintValidator<Xss, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !ReUtil.contains(HtmlUtil.RE_HTML_MARK, value);
    }

}