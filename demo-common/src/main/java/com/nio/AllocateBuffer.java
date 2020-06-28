package com.nio;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * @Date 2020/6/28 10:23
 * @name AllocateBuffer
 */


public class AllocateBuffer {

    public static void main(String[] args) {

        //堆上分配
        ByteBuffer buffer=ByteBuffer.allocate(1024000);
        System.out.println("buffer:"+buffer);
        System.out.println("after alocate:" +Runtime.getRuntime().freeMemory() );
        //直接内存分配
        ByteBuffer direct=ByteBuffer.allocateDirect(1024000);
        System.out.println("directBuffer:" +direct);
        System.out.println("after directAllocate: "+Runtime.getRuntime().freeMemory());

        AllocateBuffer.allocateCompare();
        AllocateBuffer.readCompare();

    }

    public static void allocateCompare(){
        int time=100000;
        long st=System.currentTimeMillis();
        for(int i=0;i<time;i++){
            ByteBuffer buffer=ByteBuffer.allocate(2);
        }
        long et=System.currentTimeMillis();
        System.out.println("进行"+time+"次分配操作时，堆内存分配耗时："+(et-st));

        long st1=System.currentTimeMillis();
        for(int i=0;i<time;i++){
            ByteBuffer direct=ByteBuffer.allocateDirect(2);
        }
        long et1=System.currentTimeMillis();
        System.out.println("进行"+time+"次分配操作时，直接内存分配耗时："+(et1-st1));
    }

    public static void readCompare(){
        int time=1000000;
        long st=System.currentTimeMillis();
        ByteBuffer buffer=ByteBuffer.allocate(2*time);
        for(int i=0;i<time;i++){
            buffer.putChar('a');
        }
        long et=System.currentTimeMillis();
        System.out.println("进行"+time+"次write操作时，堆内存耗时："+(et-st)+"ms");

        long st1=System.currentTimeMillis();
        ByteBuffer direct=ByteBuffer.allocateDirect(2*time);
        for(int i=0;i<time;i++){
           direct.putChar('b');
        }
        long et1=System.currentTimeMillis();
        System.out.println("进行"+time+"次write操作时，直接内存耗时："+(et1-st1)+"ms");

        System.out.println("after directAllocate: "+Runtime.getRuntime().freeMemory());

    }
}
