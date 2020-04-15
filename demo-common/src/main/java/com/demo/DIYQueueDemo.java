package com.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date 2019/11/28 14:33
 * @name DIYQueueDemo
 */

@Slf4j
public class DIYQueueDemo {

    private static final DiyQueue<String> queue=new DiyLinkedQueue<String>();

    class Product implements Runnable{
        private final String msg;
        public Product(String message){
            this.msg=message;
        }

        @Override
        public void run() {
            boolean putSuccess = queue.put(msg);
            if(putSuccess){
               log.info("put {} success",msg);
               return;
            }
            log.info("put success {}",msg);
        }
    }

    class Consumer implements Runnable{

        @Override
        public void run(){
            String message=queue.take();
            log.info("consumer message: {}",message);
        }
    }



    public void testDiyQueue() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<100;i++){
            //如果是偶数则提交生产，奇数就提交消费
            if(i%2==0){
                executorService.submit(new Product(i+""));
                continue;
            }
            executorService.submit(new Consumer());
        }
        Thread.sleep(10000);

    }



    public static void main(String[] args) throws InterruptedException {
        DIYQueueDemo demo=new DIYQueueDemo();
        demo.testDiyQueue();
        System.exit(0);
    }
}
