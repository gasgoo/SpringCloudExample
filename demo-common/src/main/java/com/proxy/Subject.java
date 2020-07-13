package com.proxy;

/**
 * @TODO
 * @Date 2019/8/2 16:23
 */
public interface Subject {

    default void request() {
        System.out.println("default默认实现");
    }
}
