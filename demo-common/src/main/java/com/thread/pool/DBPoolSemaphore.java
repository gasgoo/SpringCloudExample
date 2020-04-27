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
    //可用连接  已用连接
    private final Semaphore useFull=new Semaphore(10);

    private final Semaphore useLess=new Semaphore(0);


    private static LinkedList<Connection> poop=new LinkedList<>();
    //归还连接

}
