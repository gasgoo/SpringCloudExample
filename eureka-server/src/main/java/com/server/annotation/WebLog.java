package com.server.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 * @Date 2019/5/22 11:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {

    /*日志描述信息*/
    String descriptin() default "";
}
