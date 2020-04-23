package com.thread.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * DB connnection
 * @Date 2020/4/23 15:29
 * @name DBPool
 */


public class DBPool {

    private static LinkedList<Connection> pool=new LinkedList<>();

    public DBPool(int intitialSize){
        if(intitialSize>0){
            for(int i=0;i<intitialSize;i++){
                pool.addLast(DBConnection.fetchConnection());
            }
        }
    }

    public void release(Connection connection){
        if(connection !=null){
            synchronized (pool){
                pool.notifyAll();
            }
        }
    }

    /**
     * @Description  在mills内无法获取连接则返回null
     * @Date 2020/4/23 15:33
     **/
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills < 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
            } else {
                //超时时刻
                long future = System.currentTimeMillis() + mills;
                //等待时间
                long remaing = mills;
                //池中是空的则等待
                while (pool.isEmpty() && remaing > 0) {
                    pool.wait(remaing);
                    //唤醒一次 重新计算等待时长
                    remaing = future - System.currentTimeMillis();
                }
            }
            Connection connection = null;
            if (!pool.isEmpty()) {
                connection = pool.removeFirst();
            }
            return connection;
        }
    }
}
