package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;


/**
 * 通道测试类
 * @Date 2019/12/11 10:28
 * @name InHandlerDemoTest
 */
public class InHandlerDemoTest {

    static  Charset utf8 = Charset.forName("UTF-8");

    //入栈生命周期
    @Test
    public void testInhanddlerLIfeCircle(){

         InHandlerDemo inHandlerDemo=new InHandlerDemo();
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel cc) throws Exception {
                cc.pipeline().addLast(inHandlerDemo);
            }
        };

        //创建嵌入式通道
        EmbeddedChannel channel =new EmbeddedChannel();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        //模拟入栈 写一个数据包
        channel.writeInbound(buf);
        channel.flush();
        channel.writeInbound(buf);
        channel.flush();
        //close
        channel.close();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testByteBuf(){
        //初始容量9  最大100个byte
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(9,100);
        System.out.println("action>>>分配"+ buf);
        buf.writeBytes(new byte[]{1,2,3,4,5,6});
        System.out.println("写入数据");
        for(int i=0;i>buf.readableBytes();i++){
            System.out.println(">>>>>read byte"+ buf.getByte(i));
        }
        while(buf.isReadable()){
            System.out.println(">>>>>>"+buf.readByte());
        }

    }

    //共享head
    @Test
    public void testCompositeBuffer(){

        CompositeByteBuf buf = ByteBufAllocator.DEFAULT.compositeBuffer();

        ByteBuf header =Unpooled.copiedBuffer("组合缓冲类型Test",utf8);

        //消息体1
        ByteBuf  body =Unpooled.copiedBuffer("高性能netty body",utf8);

        buf.addComponents(header,body);
        sendMsg(buf);

        header.retain();
        buf.release();

        //消息体2  重新分配body缓存
        buf =ByteBufAllocator.DEFAULT.compositeBuffer();
        ByteBuf body2=Unpooled.copiedBuffer("高性能学习 body2",utf8);
        buf.addComponents(header,body2);
        sendMsg(buf);
        buf.release();


    }

    private void sendMsg(CompositeByteBuf buf){
        while ( buf.iterator().hasNext()){
            ByteBuf b = buf.iterator().next();
            int length=b.readableBytes();
            byte[] array =new byte[length];
            b.getBytes(b.readerIndex(),array);
            System.out.println(new String(array,utf8));
        }
        System.out.println(">>>>>>>>>>>>");
    }

    //测试多个缓冲合并成一个缓冲
    @Test
    public void testJoinBuffer(){
        CompositeByteBuf  buf =Unpooled.compositeBuffer(3);
        buf.addComponents(Unpooled.wrappedBuffer(new byte[]{1,2,3}));

        buf.addComponents(Unpooled.wrappedBuffer(new byte[]{4}));

        buf.addComponents(Unpooled.wrappedBuffer(new byte[]{5,6}));

        //合并成一个缓冲区  ByteBuffer
        ByteBuffer nioBuffer =buf.nioBuffer(0,6);
        byte[] bytes =nioBuffer.array();
        System.out.println("bytes====");
        for(byte b:bytes){
            System.out.println(b);
        }
        buf.release();

    }

    //浅层复制
    @Test
    public void testSlice(){

        ByteBuf buffer =ByteBufAllocator.DEFAULT.buffer(0,12);
        printBuffer(buffer);

        buffer.writeBytes(new byte[]{1,2,3,5,4});
        //复制  切片复制 已有的内容
        ByteBuf  slice =buffer.slice();
        System.out.println("动作 切片>>>>>");
        printBuffer(slice);

    }

    private void printBuffer(ByteBuf buffer) {
        System.out.println(">>>readerIndex: " + buffer.readerIndex() + "   writerIndex:" + buffer.writerIndex());
        System.out.println(">>>maxCapacity: " + buffer.maxCapacity() + "   capacity:" + buffer.capacity());
        System.out.println(">>>isReadable: " + buffer.isReadable() + "   isWritable:" + buffer.isWritable());
        System.out.println(">>>readableBytes: " + buffer.readableBytes() + "   writableBytes:" + buffer.writableBytes());
    }

    @Test
    public void testByteToIntegerDecoder(){
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                //channel.pipeline().addLast(new Byte2IntegerDecoder());
                channel.pipeline().addLast(new IntegerAddDecoder());
                channel.pipeline().addLast(new IntegerProcessHandler());
            }
        };
        EmbeddedChannel  em=new EmbeddedChannel(initializer);
        for(int j=0;j<100;j++){
            ByteBuf buf =Unpooled.buffer();
            buf.writeInt(j);
            em.writeInbound(buf);
        }
    }

    @Test
    public void testByteToStringDecoder() throws UnsupportedEncodingException {

        String content="疯狂创客圈，高性能学习 netty解码器";
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                //channel.pipeline().addLast(new Byte2IntegerDecoder());
                channel.pipeline().addLast(new StringReplayDecoder());
                channel.pipeline().addLast(new StringReplayHandler());
            }
        };
        EmbeddedChannel  em=new EmbeddedChannel(initializer);
        byte[] bytes =content.getBytes("UTF-8");
        for(int j=0;j<10;j++){
            Random random=new Random();
            int i = random.nextInt(3);
            System.out.println("random value:"+i);
            ByteBuf buf =Unpooled.buffer();
            buf.writeInt(bytes.length*i);
            for(int k=0;k<i;k++){
                buf.writeBytes(bytes);
            }
            //模拟入站写方法
            em.writeInbound(buf);
        }
    }

    @Test
    public void testStringEncoder(){
        String msg="疯狂创客圈，高性能学习 netty编码器";
        ChannelInitializer  initializer =new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel( EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(new String2ByteEncoder());
            }
        };
        EmbeddedChannel ch =new EmbeddedChannel(initializer);
        for(int i=0;i<10;i++){
            ch.write(msg);
        }
        ch.flush();
        //取得通道的出站数据包
        ByteBuf byteBuf =ch.readOutbound();
        while(byteBuf!=null){
            int len = byteBuf.readableBytes();
            System.out.println("可读length:"+len);
            byte[] arr=new byte[len];
            String outMsg=new String(arr);
            System.out.println("out>>>>>"+outMsg);
           // byteBuf  = ch.readOutbound();
        }
    }



}