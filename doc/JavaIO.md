
>OSI七层网络模型   物理层  链路层  网络层  传输层  会话层  表示层   应用层  
>TCP/IP模型           链路层     网络层  传输层       应用层

tcp: 面向连接的  可靠的传输协议  滑动窗口的流控
通过发送序列号和确认应答机制保证可靠传输;
>tcp三次握手的过程和序列号传输
client————syn=1 seq=j————>Server(客户端请求建立连接)
client<———syn=1 ACK=1 ack=j+1 seq=k <——server（服务端响应客户端的连接请求）
clent—>——— ACK=1 ack=k+1——————>server(客户端确认应答 连接建立)
>为什么需要三次握手?
tcp是面向连接的，所以需要双方都确认连接的建立。
> 滑动窗口
发送双方都会维护一个序列号，发送窗口的大小由接收方决定。传输过程中发送的包大小会变动。
作用: 确保数据不丢失、发送失败会重发、控制发送速度、流量控制 防止网络拥堵


udp: 面向无连接的通讯协议
速度快、容易丢失数据、通讯不需要双方确认

>BIO和NIO的区别
1. BIO面向流、NIO面向缓冲
2. 阻塞和非阻塞  
3. NIO 有select选择器 单个线程监听多个网络连接。 BIO是一个线程处理一个连接。

NIO三个特点：
1. 选择器select
2. Buffer
    capactiy
    position
    limit 
    flip() 读写模式切换
    allocate() 分配  堆内存比直接内存分配空间效率更高；直接内存读写比堆内存读写效率高。
    
3. channel  网络连接  注册到 select上
4. selectionKey

>NIO的缺陷: cpu飙升100  空轮询

Reactor反应堆模式  netty核心组件
1. Channel 网络连接 netty中需要注册到 EventLoopGroup线程组
2. 回调Callback 和Future
3. 事件和 ChannelHandler
4. EventLoopGroup EventLoop  线程池组  一个EventLoop可以处理多个Channel,但是一个Channel只会属于一个线程EventLoop处理。 
    避免了线程同步的问题、

5. ByteBuf    容量capactiy  可丢弃字节  可读字节 readIndex   可写字节 writeIndex
   分配工具类 ByteBufAllocator 
   堆缓冲区  
   直接缓冲区
   复合缓冲区
6. 半包、拆包、粘包  一次请求的数据包分散再多次发送中或 一次请求包数据中包含多个请求的数据。
   >产生原因: 应用程序的长度属性大于套接字缓冲区的大小、 ip分片、 tcp分段  tcp协议本身决定会有半包的粘包的问题。 
  处理方式：
  1. 固定长度  LineBasedFrameDecoder  
  2. 分隔符  
  3. 包中加上数据包的长度属性。
  


>通信方式
NIO  
Epoll 只linux系统支持。
OIO  old IO
Local  同台虚拟机 本地不走网关
Embedded  测试使用

