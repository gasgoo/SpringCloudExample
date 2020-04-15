package com.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Java FutureDemo 多线程实现泡茶流程    清洗茶具-烧水-泡茶
 * 相比 join方法 FutureTask可以知道线程的运行结果。
 * 异步阻塞的方法  get方法还是会阻塞的
 * @Date 2019/11/29 9:57
 * @name FutureDemo
 */

@Slf4j
public class FutureDemo extends AbstractTask{


    public static void main(String[] args) {
        Callable<Boolean> hotJob=new HotWaterJob();
        //把hotJob线程当作任务的参数提交到线程池中  由于Callable不能当作Thread的构造参数所以需要有个中间的FutureTask
        FutureTask<Boolean> htask=new FutureTask<>(hotJob);
        Thread thread=new Thread(htask,"烧水HotWater线程-thread");
        Callable<Boolean> washJob=new WashJob();
        //把hotJob线程当作任务的参数提交到线程池中  由于Callable不能当作Thread的构造参数所以需要有个中间的FutureTask
        FutureTask<Boolean> wtask=new FutureTask<>(washJob);
        Thread thread1=new Thread(wtask,"清洗washJob线程-thread");
        thread.start();
        thread1.start();
        Thread.currentThread().setName("main Thread>>>>>>");
        //获取多线程的执行结果 FutureTask的get方法
        try {
            Boolean aBoolean = htask.get();
            Boolean aBoolean1 = wtask.get();
            drinkTea(aBoolean,aBoolean1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.info(getCurThreadName()+"发生了异常......");
        }catch (Exception e){
            log.info("exception: {}",e);
        }
        log.info(getCurThreadName()+" 运行结束......");


    }

}
