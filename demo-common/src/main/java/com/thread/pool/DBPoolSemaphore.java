package com.thread.pool;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 通过Semaphore实现数据连接池
 * @Date 2020/4/26 19:25
 * @name DBPoolSemaphore
 */


public class DBPoolSemaphore {


    private final static  int pool_size=10;
    //可用连接
    private final Semaphore useFull=new Semaphore(10);
   //已用连接
    private final Semaphore useLess=new Semaphore(0);
    private static LinkedList<Connection> pool=new LinkedList<>();
    //初始化池
    static {
        for (int i = 0; i < pool_size; i++) {
            pool.addLast(DBConnection.fetchConnection());
        }
    }


    //归还连接
    public void releaseConn(Connection connection) throws InterruptedException {
        if(connection!=null){
            System.out.println("可用连接数:"+useFull.getQueueLength()+"  已用连接数"+useFull.availablePermits());
            //要操作的信号先获取
            useLess.acquire();
            synchronized (pool){
                pool.add(connection);
            }
            //往池中添加连接
            useFull.release();
        }
    }
    //获取连接
    public Connection getConnection() throws InterruptedException {
        useFull.acquire();
        Connection connection;
        synchronized (pool){
            connection=pool.removeFirst();
        }
        useLess.release();
        return connection;
    }

}
