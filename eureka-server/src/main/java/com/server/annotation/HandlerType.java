package com.server.annotation;

import java.lang.annotation.*;

/**
 * 不同的类型对应不同的实现处理注解
 * @Date 2019/9/26 9:47
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HandlerType {

    String value() default "0";
}
