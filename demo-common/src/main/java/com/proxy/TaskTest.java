package com.proxy;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Date 2019/8/14 21:37
 */
public class TaskTest {

    public static void main(String[] args) {
        //创建可缓存的线程池
        ExecutorService executorService= Executors.newFixedThreadPool(3);
        Task task=new Task();

        Future<Integer> submit = executorService.submit(task);
        Future<Integer> submit1 = executorService.submit(task);
        executorService.shutdown();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main 线程执行中");

        try {
            System.out.println("task运行返回结果:"+submit.get());
            System.out.println("task1运行返回结果:"+submit1.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("game over");
    }


}
