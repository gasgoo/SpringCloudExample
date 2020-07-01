package com.nio;




import com.google.common.base.Strings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


/**
 * @Description  
 * @Date 2020/6/28 11:42
 **/

public class NioClientHandlerA  implements Runnable {

    private Selector selector;
    private SocketChannel socketChannel;
    private String host;
    private int port;
    private volatile Boolean started;

    public NioClientHandlerA( String host, int port) {
        this.host = host;
        this.port = port;
        try{
            this.selector = Selector.open();
            socketChannel=SocketChannel.open();
            socketChannel.configureBlocking(false);
            started=true;
        }catch (Exception e){
            e.printStackTrace();
            //System.exit(-1);
        }
    }

    public void stop(){
        started=false;
    }

    @Override
    public void run() {
        try{
            doConnection();
        }catch (Exception e){
            e.printStackTrace();
            //System.exit(-1);
        }
        while(started){
            try{
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey selectionKey=null;
                while(iterator.hasNext()){
                    selectionKey=iterator.next();
                    iterator.remove();
                    inputEventHandler(selectionKey);
                }


            }catch (Exception e){
                e.printStackTrace();
                //System.exit(-1);
            }
        }
        if(selector!=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @Description  如果没有连接上则继续处理连接事件
     * @Date 2020/6/28 10:48
     **/
    private void doConnection() throws IOException {
        if(socketChannel.connect(new InetSocketAddress(host,port))){

            System.out.println("客户端启动成功>>>>>>>");
            socketChannel.write(Charset.forName("UTF-8").encode("Hello Server,I am NIOClient"));

            socketChannel.register(selector,SelectionKey.OP_READ);

            //2 向服务器发送数据
            Scanner scanner=new Scanner(System.in);
            while(scanner.hasNextLine()){
                String request=scanner.nextLine();
                if(!Strings.isNullOrEmpty(request)){
                    socketChannel.write(Charset.forName("UTF-8").encode(request));
                }
            }
        }else{
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }
    /**
     * @Description  输入事件处理
     * @Date 2020/6/28 10:55
     **/
    private void inputEventHandler(SelectionKey selectionKey) throws IOException {
        //从selectkey中获取到已经就绪 的channel 创建Buffer 循环读取客户端请求信息 将channel注册到selector上 收取客户端请求信息
        if(selectionKey.isValid()){

            SocketChannel sc = (SocketChannel) selectionKey.channel();
            if(selectionKey.isConnectable()){
                if(sc.finishConnect()){
                    //如果完成连接事件则注册读事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else {
                    System.exit(-1);
                }

            }
            //读取事件处理
            if(selectionKey.isReadable()){
                ByteBuffer buffer=ByteBuffer.allocate(1024);
                int read = sc.read(buffer);
                String response=null;
                if(read>0){
                    buffer.flip();
                    response += Charset.forName("UTF-8").decode(buffer);
                }else if(read<0){
                    System.out.println("网络连接关闭了!");
                    selectionKey.cancel();
                    sc.close();
                }else{
                    selectionKey.cancel();
                    sc.close();
                }
                System.out.println("httpServer response: "+response);
            }
        }


    }

    //发送数据
    public void sendMsg(String msg) throws IOException {
        doWrite(socketChannel,msg);
    }

    private void doWrite(SocketChannel socketChannel,String request) throws IOException {
        byte[] bytes = request.getBytes();
        ByteBuffer writeBuf=ByteBuffer.allocate(bytes.length);
        writeBuf.put(bytes);
        writeBuf.flip();
        socketChannel.write(writeBuf);

    }

}
