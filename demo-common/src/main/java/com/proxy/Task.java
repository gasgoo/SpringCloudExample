package com.proxy;

import java.util.concurrent.Callable;

/**
 * 多线程使用实例  实现 Callable接口 由返回值
 * @Date 2019/8/14 21:35
 */
public class Task implements Callable<Integer> {

    @Override
    public Integer  call() throws Exception {
        synchronized (this) {
            System.out.println("子线程在进行计算" + Thread.currentThread());
            System.out.println("start===" + Thread.currentThread().getName());
            Integer calc = calc();
            System.out.println("end===" + Thread.currentThread().getName());
            return calc;
        }

    }

    public  Integer calc(){
        int sum = 0;
        for(int i=0;i<1000;i++){
            sum ++;
        }
        return sum;
    }

    public static  int  square(int n){
        int k=0;
        while(true){
            if(k==n*n){
                return  k;
            }
            k++;
        }
    }


}