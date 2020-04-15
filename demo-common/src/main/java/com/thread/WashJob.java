package com.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 清洗茶具线程job
 * @Date 2019/11/29 10:24
 * @name WashJob
 */

@Slf4j
public class WashJob implements Callable<Boolean> {

    public static final int SLEEP_GAP=500;
    @Override
    public Boolean call() throws Exception {
        try{
            log.info("洗茶缸");
            log.info("洗茶杯");
            log.info("洗茶叶");
            Thread.sleep(SLEEP_GAP);
            log.info("清洗完成....");
        }catch (Exception e){
            log.info("WashJob Exception",e);
            return false;
        }
        return true;
    }
}
