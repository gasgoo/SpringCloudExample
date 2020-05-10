package com.thread;

import org.omg.SendingContext.RunTime;

/**
 * @Date 2020/4/20 17:54
 * @name ExpressTest
 */


public class ExpressTest {
    private static Express express=new Express(0,Express.city);


    private static class CheckKm extends Thread{
        @Override
        public void run(){
            express.waitKm();
        }
    }
    private static class CheckSite extends Thread{
        @Override
        public void run(){
            express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<3;i++){
            new CheckKm().start();
        }
        for(int i=0;i<3;i++){
            new CheckSite().start();
        }
        Thread.sleep(1000);
        express.checkKm();
        express.checkSite();
        System.out.println("cpu核数:"+ Runtime.getRuntime().availableProcessors());
        System.out.println("=="+Runtime.getRuntime().totalMemory()/2014);
    }
}
