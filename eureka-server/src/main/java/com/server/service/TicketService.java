package com.server.service;

import com.server.redis.RedissonLocker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * 售票服务  使用redisLock
 *
 * @Date 2020/7/14 11:02
 * @name TicketService
 */

@Service
@Slf4j
public class TicketService {

    private static final String KEY = "Ticket_LOCK_KEY";

    private static int ticketCount = 20;
    private CountDownLatch countDownLatch;
    @Autowired
    private RedissonLocker redisLock;

    public int sale() {
        countDownLatch = new CountDownLatch(5);
        log.info("====ticketCount=20 5个窗口售卖");
        new PlusThread().start();
        new PlusThread().start();
        new PlusThread().start();
        new PlusThread().start();
        new PlusThread().start();
        return ticketCount;
    }


    public class PlusThread extends Thread {
        //抢票的张数
        private int amount = 0;

        @SneakyThrows
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + "开始售票");
            countDownLatch.countDown();
            if (countDownLatch.getCount() == 0) {
                log.info("===========没票了");
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (ticketCount > 0) {
                redisLock.lock(KEY);
                try {
                    if (ticketCount > 0) {
                        amount++;
                        ticketCount--;
                    }
                } finally {
                    redisLock.unlock(KEY);
                }
                Thread.sleep(1000);
            }
            log.info(Thread.currentThread().getName() + "卖出" + amount + "张票");
        }
    }
}
