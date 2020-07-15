package com.zk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date 2020/7/10 15:25
 **/
public class OrderNumGenerator {
    //全局订单id
    private static int count = 0;


    //以lock的方式解决
    public String getNumber() {
        try {
            SimpleDateFormat simpt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String s = simpt.format(new Date()) + "-" + ++count;
            return s;
        } finally {
        }
    }

}


