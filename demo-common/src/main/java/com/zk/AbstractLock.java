package com.zk;

import org.I0Itec.zkclient.ZkClient;

/**
 * @Date 2020/7/10 15:51
 * @name AbstractLock
 */


public abstract class AbstractLock implements ExLock {

    //将重复代码抽象到子类中（模板方法设计模式）
    private static final String CONNECTION = "localhost:2181";
    protected ZkClient zkClient = new ZkClient(CONNECTION);
    protected static final String PATH = "/lock";

    //获取锁
    @Override
    public void lock() {
        //1、连接zkClient 创建一个/lock的临时节点
        // 2、 如果节点创建成果，直接执行业务逻辑，如果节点创建失败，进行等待
        if (tryLock()) {
            System.out.println("#####成功获取锁######");
        } else {
            //进行等待
            waitLock();
        }

        //3、使用事件通知监听该节点是否被删除    ，如果是，重新进入获取锁的资源

    }

    //创建失败 进行等待
    abstract void waitLock();


    abstract boolean tryLock();


    //释放锁
    @Override
    public void unLock() {
        //执行完毕 直接连接
        if (zkClient != null) {
            zkClient.delete(PATH);
            System.out.println("######释放锁完毕######");
        }

    }

}
