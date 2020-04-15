package com.common;

import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;

/**
 * @TODO
 * @Date 2019/12/5 10:58
 * @name mapTest
 */


public class mapTest {


    @Test
    public void testDeadLoopMap() throws InterruptedException {
            HashMap<String,String> map =new HashMap<>(2);
            Thread t =new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10000;i++){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                map.put(UUID.randomUUID().toString(),"");
                            }
                        },"Thread-"+i).start();
                    }
                }
            },"thread");
            t.start();
            //t.join();

        System.out.println(map.size());
        }

}
