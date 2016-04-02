package com.demeter.framework.spring.aop.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by eric on 3/25/16.
 */

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationRule {

    String value();

    Class<?> validatorType() default Object.class;

    String method() default "";

    String errorCode() default "";

}
