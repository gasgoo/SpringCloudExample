package com.zk;

import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * zk分布式锁
 *
 * @Date 2020/7/10 15:12
 * @name ZkLockUtils
 */


public class ZkLockUtils extends AbstractLock {

    // zk连接地址
    private static final String CONNECTSTRING = "127.0.0.1:2181";
    // 创建zk连接
    private ZkClient zkClient = new ZkClient(CONNECTSTRING);
    protected static final String PATH = "/lock";
    private CountDownLatch countDownLatch = null;
    //当前请求的节点前一个节点
    private String beforePath;
    //当前请求的节点
    private String currentPath;


    @Override
    public boolean tryLock() {
        if (currentPath == null || currentPath.length() <= 0) {
            //创建一个临时顺序节点
            currentPath = this.zkClient.createEphemeralSequential(PATH + "/", "node");
        }
        //获取所有节点并排序
        List<String> children = this.zkClient.getChildren(PATH);
        Collections.sort(children);
        //如果当前节点在所有节点中排名第一则获取锁成功
        if (currentPath.equals(PATH + "/" + children.get(0))) {
            return true;
        } else {
            //如果当前节点在所有节点中排名中不是排名第一，则获取前面的节点名称，并赋值给beforePath
            int wz = Collections.binarySearch(children, currentPath.substring(7));
            beforePath = PATH + "/" + children.get(wz - 1);
        }
        return false;

    }

    @Override
    public void unlock() {
        if (zkClient != null) {
            zkClient.delete(PATH);
            zkClient.close();
        }
    }

}
