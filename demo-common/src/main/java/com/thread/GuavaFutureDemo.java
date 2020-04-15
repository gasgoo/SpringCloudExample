package com.thread;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @ guava 异步回调的多线程处理方式
 * @Date 2019/11/29 10:26
 * @name GuavaFutureDemo
 */

@Slf4j
public class GuavaFutureDemo extends AbstractTask {


    public static void main(String[] args) {
        MainJob mainJob=new MainJob();
        Thread mainThread =new Thread(mainJob);
        mainThread.setName("main thread");
        mainThread.start();
        Callable<Boolean> hotJob=new HotWaterJob();
        Callable<Boolean> washJob=new WashJob();

        //创建线程池
        ExecutorService javaPool = Executors.newFixedThreadPool(10);

        //包装java线程池 构建Guava线程池
        ListeningExecutorService guavaPool = MoreExecutors.listeningDecorator(javaPool);
        ListenableFuture<Boolean> hotFuture = guavaPool.submit(hotJob);
        //帮定异步回调，烧水完成后，把烧水线程的结果
        hotFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    Boolean aBoolean = hotFuture.get();
                    if(aBoolean){
                        mainJob.waterOk=true;
                    }else{
                        log.info("烧水线程失败了....");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        },guavaPool);
        ListenableFuture<Boolean> washFuture = guavaPool.submit(washJob);
        washFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    Boolean r = washFuture.get();
                    if(r){
                        mainJob.cupOk=true;
                    }else{
                        log.info("washJob线程失败了....");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        },guavaPool);


    }


}
