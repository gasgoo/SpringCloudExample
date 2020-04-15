package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类  实现Invoke接口
 * @Date 2019/8/2 16:31
 */
public class JdkProxySubject implements InvocationHandler {


    private Subject realSubject;

    public JdkProxySubject(RealSubject realSubject){
        this.realSubject=realSubject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理演示");
        Object result=null;
        try {
            result=method.invoke(realSubject,args);
        }catch (Exception e){
            System.out.println("ex:"+e.getMessage());
        }finally {
            System.out.println("after ");
        }
        return result;
    }
}
