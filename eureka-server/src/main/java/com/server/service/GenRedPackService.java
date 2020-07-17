package com.server.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程模拟红包池初始化
 *
 * @Date 2020/7/16 13:56
 * @name RedPackServer
 */

@Slf4j
@Service
public class GenRedPackService {

    private static int threadCount = 20;
    public static int honBaoCount = 1000;
    //LIST类型来模拟红包池子
    public static String hongBaoPoolKey = "hongBaoPoolKey";
    //LIST类型，记录所有用户抢红包的详情
    public static String hongBaoDetailListKey = "hongBaoDetailListKey";
    //记录已经抢过红包的用户ID,防止重复抢
    public static String userIdRecordKey = "userIdRecordKey";

    final CountDownLatch latch = new CountDownLatch(threadCount);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisScript redisScript;

    public void genRedPack() {

        for (int i = 0; i < threadCount; i++) {
            new GenTask(i).start();
        }

    }

    class GenTask extends Thread {
        private int page;

        GenTask(int page) {
            this.page = page;
        }

        @Override
        public void run() {
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //每个线程要初始化多少个红包  20线程
            int per = honBaoCount / threadCount;
            JSONObject object = new JSONObject();
            for (int j = page * per; j < (page + 1) * per; j++) {
                //红包ID
                object.put("id", "rid_" + j);
                //红包金额
                object.put("money", j);
                redisTemplate.boundListOps("hongBaoPoolKey").leftPush(object.toJSONString());

            }
        }
    }

    public void getHongBao() {
        final CountDownLatch latch = new CountDownLatch(threadCount);//20 //发枪器
        for (int i = 0; i < threadCount; i++) {  //20线程
            Thread thread = new Thread() {

            };
            thread.start();

        }
    }

    class GetTask extends Thread {

        @Override
        public void run() {
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                //模拟一个用户ID使用UUID XXXX
                String userid = UUID.randomUUID().toString();
                /**
                 * List设置lua的KEYS
                 */
                List<String> keyList = new ArrayList<>();
                keyList.add("hongBaoPoolKey");
                keyList.add("hongBaoDetailListKey");
                keyList.add("userIdRecordKey");
                keyList.add("userid");
                //设置参数
                Map<String, Object> argvMap = new HashMap<String, Object>();
                argvMap.put("expire", 10000);
                argvMap.put("times", 10);
                //抢红包  eval 可以直接调用我们LUA脚本里的业务代码  object ={rid_1:1, money:9, userid:001}
                Object object = redisTemplate.execute(redisScript, keyList, argvMap);
                if (null != object) {
                    System.out.println("用户ID号：" + userid + " 抢到红包的详情是 " + object);
                } else {
                    if (redisTemplate.boundListOps("hongBaoPoolKey").size() == 0) {
                        break;
                    }
                }
            }
        }
    }


    public void redPack() {
        genRedPack();
        getHongBao();
    }


}
