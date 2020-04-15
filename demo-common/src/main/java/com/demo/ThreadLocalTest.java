package com.demo;

/**
 * @Date 2020/2/27 9:33
 * @name ThreadLocalTest
 */


public class ThreadLocalTest {

    static class ResourceClass {
        public final static ThreadLocal<String> t1 = new ThreadLocal<>();
        public final static ThreadLocal<String> t2 = new ThreadLocal<>();
    }

    static class A{
        public void setOne(String value) {
            ResourceClass.t1.set(value);
        }

        public void setTwo(String value) {
            ResourceClass.t2.set(value);
        }
    }

    static class B {
        public void display() {
            System.out.println(ResourceClass.t1.get()
                    + ":" + ResourceClass.t2.get());
        }
    }

    public static void main(String []args) {
        final A a = new A();
        final B b = new B();
        for(int i = 0 ; i < 15 ; i ++) {
            final String resouce1 = "线程-" + i;
            final String resouce2 = " value = (" + i + ")";
            new Thread() {
                public void run() {
                    try {
                        a.setOne(resouce1);
                        a.setTwo(resouce2);
                        b.display();
                    }finally {
                        ResourceClass.t1.remove();
                        ResourceClass.t2.remove();
                    }
                }
            }.start();
        }

    }
 }
