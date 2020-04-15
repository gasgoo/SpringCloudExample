package com.server.annotation;

import java.lang.annotation.*;

/**
 * 权限类型注解
 * @Date 2019/9/9 18:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface PermessionType {

    /**
     * 登陆拦截 (默认拦截)
     */
    boolean loginState() default true;

    /**
     * 权限编号 (默认不拦截)
     */
    int permessionNum() default 0;

    /**
     * 随机验证码拦截 (默认不拦截)
     */
    boolean randCode() default false;
}
