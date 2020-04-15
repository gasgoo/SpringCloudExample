package com.lock;

/**
 * 对象头结构  64位虚拟机 markWord是64bit  classPointer也有可能是64bit 会压缩
 * @Date 2020/3/31 9:19
 * @name ObjectHeader
 */


public class ObjectHeader {

    /**
     * markWord是64bit 存储的东西不固定  对象状态- 无锁=01  偏向锁= 轻量级锁  重量级锁  GC标记
     * 00000000  00000000  00000000  00000000   00000000  00000000  00000000  00000000
     *  对象的hashcode 25bit
     *  分带年龄 4bit
     *  是否偏向锁 1bit  0  1
     *  锁标识位 2bit  无锁=01  偏向锁=01  轻量级锁=00  重量级锁=10  GC标记=11
     *  轻量级锁抢锁失败，jvm会使用自旋锁，不断重试  失败则升级位 重量级10;
     */
    private String markWord;
    /**
     * classPointer 32bit
     */
    private String classPointer;



}
