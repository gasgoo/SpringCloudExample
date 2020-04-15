package com.proxy;

/**
 * 静态代理对象
 * @Date 2019/8/2 16:25
 */
public class ProxyStatic implements Subject {

    private RealSubject realSubject;

    public ProxyStatic(RealSubject realSubject){
        this.realSubject=realSubject;
    }
    @Override
    public void request() {
        System.out.println("静态代理演示");
        try{
            realSubject.request();
        }catch (Exception e){
            System.out.println("==="+e.getMessage());
            throw e;
        }

    }
}
