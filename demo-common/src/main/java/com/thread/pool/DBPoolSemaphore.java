package com.thread.pool;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 通过Semaphore实现数据连接池
 * @Date 2020/4/26 19:25
 * @name DBPoolSemaphore
 */


public class DBPoolSemaphore {


    private final static  int POOL_SIZE=10;
    //可用连接
    private final Semaphore useFull=new Semaphore(10);
    //已用连接
    private final Semaphore useLess=new Semaphore(0);


    private static LinkedList<Connection> pool=new LinkedList<>();
    //初始化连接池
    static{
        for(int i=0;i<POOL_SIZE;i++){
            pool.addLast(DBConnection.fetchConnection());
        }
    }

    //用完归还连接
    public void returnConnection(Connection connection) throws InterruptedException {
        if(connection!=null){
            System.out.println("当前有"+useFull.getQueueLength()+"个线程等待数据库的连接!");
            useLess.acquire();
            synchronized (pool){
                pool.addLast(connection);
            }
        }
    }

    /**
     * @Description  从池中取数据库连接
     * @Date 2020/5/6 11:37
     **/
    public Connection takeConnection() throws InterruptedException {
        //获取许可证
        useFull.acquire();
        Connection connection;
        synchronized (pool){
             connection = pool.removeFirst();
        }
        //归还许可证
        useLess.release();
        return connection;
    }

    public void closeConnnection(){
        if(useFull.getQueueLength()>0){
            useFull.release();
        }

    }

}
