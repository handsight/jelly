package com.mall.jelly.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebLog {
    String value() default "";

    /**
     * 是否忽略注解 默认否
     * @return
     */
    boolean ignore() default false;
}
