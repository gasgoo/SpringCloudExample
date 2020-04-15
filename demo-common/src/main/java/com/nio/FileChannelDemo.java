package com.nio;


import org.apache.commons.io.FileUtils;
import org.mockito.internal.util.io.IOUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Date 2019/11/27 9:25
 * @name FileChannelDemo
 */


public class FileChannelDemo {


    public static void main(String[] args) {
        FileChannelDemo channelDemo=new FileChannelDemo();
        String srcPath="C:\\data\\bootlesson\\demo-common\\src\\main\\resources\\srcText.txt";
        String destPath="C:\\data\\bootlesson\\demo-common\\src\\main\\resources\\destText.txt";
        channelDemo.nioFielCopy(srcPath,destPath);
        channelDemo.oldNioCopyFIle(srcPath,destPath);
    }

    public void nioFielCopy(String srcPath,String destPath) {
        FileInputStream fis=null;
        FileOutputStream fos=null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        long starttime=System.currentTimeMillis();
        try {
            File srcFile = new File(srcPath);
            fis = new FileInputStream(srcFile);
            inChannel = fis.getChannel();
            fos = new FileOutputStream(new File(destPath));
            outChannel = fos.getChannel();

            //获取一个字节缓冲区  内存中的一个数组 大小是1024个Byte  默认是写模式
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int length = -1;
            //从输入通道读取数据到缓冲区
            while ((length = inChannel.read(buffer)) != -1) {
                //缓存写 翻转为读取模式
                buffer.flip();
                int outlength = 0;
                //将buffer中的内容写入输出通道
                while ((outlength = outChannel.write(buffer)) != 0) {
                    System.out.println("写入的字节数:" + outlength);
                }
                //切换为写模式
                buffer.clear();
            }
            //强制刷盘
            outChannel.force(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(outChannel);
            IOUtil.closeQuietly(fos);
            IOUtil.closeQuietly(inChannel);
            IOUtil.closeQuietly(fis);

        }
        long endTime=System.currentTimeMillis();
        System.out.println("===耗时毫秒数:"+(endTime-starttime));


    }

    public void oldNioCopyFIle(String srcPath,String destPath){
        FileInputStream fis=null;
        FileOutputStream fos=null;
        StringBuilder sb=new StringBuilder();
        File destFile=null;
        long startTime=System.currentTimeMillis();
        try {
            File srcFile = new File(srcPath);
             destFile=new File(destPath);
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            byte[] buffer=new byte[1024];
            int length=0;
            while(fis.read()!=-1){
                length=fis.read(buffer);
                String str=new String(buffer,0,length);
                fos.write(str.getBytes("UTF-8"));
                sb.append(str);
            }
            System.out.println("===读取的长度:"+sb.length());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtil.closeQuietly(fos);
            IOUtil.closeQuietly(fis);
            FileUtils.deleteQuietly(destFile);
        }
        long endTime=System.currentTimeMillis();
        System.out.println("===oldIO耗时毫秒数:"+(endTime-startTime));
    }
}
