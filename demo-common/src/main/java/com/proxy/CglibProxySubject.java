package com.proxy;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Date 2019/8/2 17:05
 */
public class CglibProxySubject implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib 动态代理");
        Object result=null;
        try {
            result=methodProxy.invokeSuper(o,objects);
            method.invoke(o,objects);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw  e;
        }finally {
            System.out.println("cglib after");
        }
        return result;
    }
}
