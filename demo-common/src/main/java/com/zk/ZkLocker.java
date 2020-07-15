package com.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * zk分布式锁
 *
 * @Date 2020/7/10 15:12
 * @name ZkLocker
 */


public class ZkLocker extends AbstractLock {


    private CountDownLatch countDownLatch = new CountDownLatch(50);
    //当前请求的节点前一个节点
    private String beforePath;
    //当前请求的节点
    private String currentPath;


    @Override
    void waitLock() {
        System.out.println(">>>>>>排队等待获取锁");
        IZkDataListener listener = new IZkDataListener() {

            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        };
        //监听事件通知
        zkClient.subscribeDataChanges(PATH, listener);
        if (zkClient.exists(PATH)) {
            try {
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //为了不影响程序的执行 建议删除该事件监听 监听完了就删除掉
        zkClient.unsubscribeDataChanges(PATH, listener);

    }

    @Override
    public boolean tryLock() {
       /* if (currentPath == null || currentPath.length() <= 0) {
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
        return false;*/
        try {
            zkClient.createEphemeral(PATH);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


}
