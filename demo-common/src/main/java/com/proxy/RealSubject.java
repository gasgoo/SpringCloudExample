package com.proxy;

import java.lang.reflect.Proxy;

/**
 * 真实的接口实现
 * @Date 2019/8/2 16:23
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("real exec request");
    }

    public static void main(String[] args) {
        Subject subject=new ProxyStatic(new RealSubject());
        subject.request();
        System.out.println("jdk动态代理======");
        Subject subject1= (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(),
                new Class[]{Subject.class},new JdkProxySubject(new RealSubject()));
        subject1.request();
        System.out.println("cglib动态代理====");
        /*Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(Subject.class);
        enhancer.setCallback(new CglibProxySubject());
        Subject subject2= (Subject) enhancer.create();
        subject2.request();*/

    }
}
