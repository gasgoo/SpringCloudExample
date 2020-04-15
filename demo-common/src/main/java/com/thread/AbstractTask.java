package com.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2019/11/29 10:27
 * @name AbstractTask
 */


@Slf4j
public abstract class AbstractTask {

    public static final int SLEEP_GAP=500;


    public static String getCurThreadName(){
        return Thread.currentThread().getName();
    }

    public static void drinkTea(boolean waterOk,boolean cupOk){
        if(waterOk &&cupOk){
            log.info("泡茶喝,可以开始煮酒论英雄了....");
        }else if(!waterOk){
            log.info("烧水线程失败，没法泡茶");
        }else if(!cupOk){
            log.info("清洗线程失败，没法泡茶");
        }
    }

   static class MainJob implements Runnable{
        boolean waterOk =false;
        boolean cupOk = false;
        int gap = SLEEP_GAP/10;

        @Override
        public void run() {
            if(waterOk && cupOk){
                drinkTea(waterOk,cupOk);
            }
        }
    }



}
