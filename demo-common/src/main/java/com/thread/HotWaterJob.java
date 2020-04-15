package com.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 烧水任务
 * @Date 2019/11/29 10:22
 * @name HotWaterJob
 */

@Slf4j
public class HotWaterJob implements Callable<Boolean> {

    public static final int SLEEP_GAP=500;
    @Override
    public Boolean call() throws Exception {
        try{
            log.info("洗好水壶");
            log.info("洗好水杯");
            log.info("灌装好凉水");
            log.info("放在火炉上 烧水");
            Thread.sleep(SLEEP_GAP);
            log.info("水烧开了....");
        }catch (Exception e){
            log.info("hotWater Exception",e);
            return false;
        }
        return true;
    }
}
