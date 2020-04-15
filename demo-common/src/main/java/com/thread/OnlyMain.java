package com.thread;

import org.assertj.core.util.Preconditions;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Date 2020/4/9 17:19
 * @name OnlyMain
 */


public class OnlyMain {

    public static void main(String[] args) throws InterruptedException {
        OnlyMain.addListen();
        Thread endThread=new UseThread("endThread");
        endThread.start();
        endThread.interrupt();
    }

    public static  void addListen(){
        //Java虚拟机线程系统的管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要monitors和Synchronizes信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, false);
        for(ThreadInfo info:threadInfos){
            System.out.println("线程ID:"+info.getThreadId()+  "--name:"+info.getThreadName());
        }
    }
    //定义线程
    public static class UseThread extends Thread{
        public UseThread(String name){
            super(name);
        }

        @Override
        public void run(){
            String threadName=Thread.currentThread().getName();
            System.out.println(threadName+" 中断标识="+isInterrupted());
            //当没有中断
            while(!isInterrupted()){
                //while(interrupted()){
                System.out.println(threadName +" is running");
                System.out.println(threadName +"inner interrrupt flag="+isInterrupted());
            }
            System.out.println("线程已经中断了 中断标识interrrupt flag="+isInterrupted());
        }
    }


}
