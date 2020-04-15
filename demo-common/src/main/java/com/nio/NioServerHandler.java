package com.nio;

import com.google.common.base.Strings;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 持续接收客户端内容
 *
 * @Date 2019/11/8 16:46
 * @name NioServerHandler
 */


public class NioServerHandler implements Runnable {

    private Selector selector;

    public NioServerHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {

        for (; ; ) {
            //可用的channel数量
            int readyChannels = 0;
            try {
                readyChannels = selector.select();

                if (readyChannels == 0) {
                    continue;
                }
                //可用channel的集合
                Set<SelectionKey> channelLists = selector.selectedKeys();
                Iterator iterator = channelLists.iterator();
                while (iterator.hasNext()) {

                    SelectionKey selectionKey = (SelectionKey) iterator.next();
                    //移除当前拿到的channel
                    iterator.remove();
                    //如果是可读事件
                    if (selectionKey.isReadable()) {
                        readHandler(selectionKey, selector);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //可读事件处理
    private void readHandler(SelectionKey selectionKey, Selector selector) throws IOException {
        //从selectkey中获取到已经就绪 的channel 创建Buffer 循环读取客户端请求信息 将channel注册到selector上 收取客户端请求信息
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String response = "";
        while (socketChannel.read(byteBuffer) > 0) {
            //切换buffer为读模式
            byteBuffer.flip();
            response += Charset.forName("UTF-8").decode(byteBuffer);
        }
        socketChannel.register(selector, SelectionKey.OP_READ);
        if (response.length() > 0) {
            System.out.println("收到的请求内容: " + response);
        }
    }
}
