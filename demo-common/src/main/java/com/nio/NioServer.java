package com.nio;

import com.google.common.base.Strings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * NIOServer服务器端
 *
 * @Date 2019/11/8 11:06
 * @name NioServer
 */


public class NioServer {


    private Map<SocketChannel,ClientModel> clientModelMap=new HashMap<>();
    private Selector selector=null;
    private ByteBuffer buffer=ByteBuffer.allocate(1024);
    private Charset charset=Charset.forName("UTF-8");
    private  int count=0;


    /**
     * @Description 开启
     * @Date 2019/11/8 11:07
     **/
    public void start() throws IOException {
        //1 创建selector
         selector = Selector.open();
        //2 通过ServerSocketChannel创建channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //3 帮定监听端口
        serverSocketChannel.bind(new InetSocketAddress(8000));
        //4 设channle为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //5 将channel注册到selector上，监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动成功,port=8000 address: " + serverSocketChannel.getLocalAddress());
        //6 循环等待新接入的连接
        for (; ; ) {
            //可用的channel数量
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }
            //可用channel的集合
            Set<SelectionKey> channelLists = selector.selectedKeys();
            Iterator iterator = channelLists.iterator();
            while (iterator.hasNext()) {
                //获取可用的通道
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                //如果是接入事件
                if(selectionKey.isAcceptable()){
                    acceptHandler(selectionKey);
                }
                //如果是可读事件
                if(selectionKey.isReadable()){
                    readHandler(selectionKey);
                }
                //可写事件就绪
                if(selectionKey.isWritable()){
                    wirteHandler(selectionKey);

                }
                //移除当前拿到的channel
                iterator.remove();
            }

        }
        //7  根据就绪状态，调用业务逻辑处理

    }

    private void wirteHandler(SelectionKey selectionKey) throws IOException {
        buffer.clear();
        SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
        String msg_from_server="Hello client...."+socketChannel.getLocalAddress();
        buffer.put(msg_from_server.getBytes(Charset.forName("UTF-8")));
        buffer.flip();
        socketChannel.write(buffer);
        System.out.println("服务端发送的数据>>>>"+msg_from_server);
        socketChannel.register(selector,SelectionKey.OP_READ);
    }


    /*
     * @Description 接入事件处理器
     * @Date 2019/11/8 11:28
     **/
    private void acceptHandler( SelectionKey selectionKey) throws IOException {
        //如果是接入事件，创建socketChannle  设置为非阻塞模式 注册selector上监听可读事件 回复客户端提示信息
        ServerSocketChannel serverChanel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverChanel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector,SelectionKey.OP_READ);
        if(socketChannel.isConnected()){
            socketChannel.write(Charset.forName("UTF-8").encode("我们已经是好朋友了，可以开始聊天了！"));
            System.out.println("服务器已经和客户端建立了连接!");
            this.saveClientChannel(socketChannel);
        }

    }

    private void saveClientChannel(SocketChannel socketChannel) throws IOException {
        ClientModel clientModel=new ClientModel();
        clientModel.setSocketAddress((InetSocketAddress) socketChannel.getRemoteAddress());
        clientModelMap.put(socketChannel,clientModel);
    }

    //可读事件处理
    private void readHandler(SelectionKey selectionKey) throws IOException {
        //从selectkey中获取到已经就绪 的channel 创建Buffer 循环读取客户端请求信息 将channel注册到selector上 收取客户端请求信息
         SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
        this.saveClientChannel(socketChannel);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String request="";
        while(socketChannel.read(byteBuffer)>0){
            //切换buffer为读模式
            byteBuffer.flip();
            request+=Charset.forName("UTF-8").decode(byteBuffer);
        }
        socketChannel.register(selector,SelectionKey.OP_READ);
        if(request.length()>0){
            System.out.println("服务器收到的请求内容:>>> "+request);
            //群聊
            this.broadCast(selector,socketChannel,request);
        }
        //将此对应的channel设置为准备下一次接收数据
        selectionKey.interestOps(SelectionKey.OP_READ);
        socketChannel.register(selector,SelectionKey.OP_READ);
        System.out.println("广播通知>>>>>>");

        //新开线程 持续接收客户端的请求
        new Thread(new NioServerHandler(selector)).start();
        //响应客户端内容
        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNextLine()){
            String response=scanner.nextLine();
            if(!Strings.isNullOrEmpty(request)){
                socketChannel.write(Charset.forName("UTF-8").encode(response));
            }
        }
    }

    private void broadCast(Selector selector,SocketChannel socketChannel,String request) throws IOException {
        //获取所有已接入的客户端channel
        Set<SelectionKey> keys = selector.keys();
        this.saveClientChannel(socketChannel);
        System.out.println("当前在线连接数:"+onlineNum(selector));
        for(SelectionKey key:keys){
            SelectableChannel channel = key.channel();
            if(channel instanceof SocketChannel && channel!=socketChannel){
                SocketChannel dest = (SocketChannel) channel;
                dest.write(charset.encode(request));
            }
        }

    }

    private  int onlineNum(Selector selector){
        for(SelectionKey key:selector.keys()){
            SelectableChannel channel = key.channel();
            if(channel instanceof SocketChannel ){
               count++;
            }
        }
        return count;
    }




    public static void main(String[] args) throws IOException {
        NioServer nioServer=new NioServer();
        nioServer.start();
    }
}
