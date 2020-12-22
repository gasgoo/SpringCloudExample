package com.cloud.anno;


import java.lang.annotation.*;

/**
 * @Description 自顶一个注解 效果等同于 @Service @Component
 * @Date 2020/12/17 14:00
 **/

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyService {
}
