package com.thread.pool;

import java.sql.Connection;
import java.util.Random;

/**
 * @Date 2020/5/6 11:44
 * @name SemaphorePoolTest
 */


public class SemaphorePoolTest {

    private static final DBPoolSemaphore pool=new DBPoolSemaphore();

    private static class BizThread extends Thread{
        @Override
        public void run(){
            Random r=new Random();
            long start=System.currentTimeMillis();
            try{
                Connection connection=pool.takeConnection();
                Thread.sleep(300+r.nextInt(100));
                System.out.println("获取连接耗时:"+(System.currentTimeMillis()-start)+"毫秒");
                System.out.println("数据库查询完成,释放连接!");
                pool.returnConnection(connection);
            } catch (InterruptedException e) {
                pool.closeConnnection();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Thread thread = new BizThread();
            thread.start();
        }
    }
}
