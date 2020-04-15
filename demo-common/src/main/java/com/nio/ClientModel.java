package com.nio;

import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;

/**
 * 客户端连接对象
 * @Date 2019/11/27 14:52
 * @name ClientModel
 */

@Getter
@Setter
public class ClientModel {

    String fileName;
    long fileLength;
    long startTime;
    InetSocketAddress socketAddress;
    FileChannel fileChannel;
}
