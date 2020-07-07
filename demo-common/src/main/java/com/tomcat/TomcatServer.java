package com.tomcat;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建tomcat Server
 *
 * @Date 2020/7/6 15:23
 * @name TomcatServer
 */

@Slf4j
public class TomcatServer {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        log.info("Server start on 8000");
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            executorService.execute(() -> {
                try {
                    InputStream inputStream = socket.getInputStream();
                    log.info("收到请求....");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String msg = null;
                    StringBuilder request = new StringBuilder();
                    while ((msg = reader.readLine()) != null) {
                        if (msg.length() == 0) break;
                        request.append(msg).append("\r\n");
                    }
                    //请求头部解析
                    String firstLine = request.toString().split("\r\n")[0];
                    log.info("firstLine:{}", firstLine);
                    String path = firstLine.split(" ")[1];
                    log.info("requst servlet path:{}", path);

                    //response
                    OutputStream outputStream = socket.getOutputStream();
                    byte[] response = "Hello Kitty Welcome to here!".getBytes();
                    outputStream.write("HTTP/1.1 200 OK \r\n".getBytes());
                    outputStream.write("Content-Type:text/html;charset=utf-8\r\n".getBytes());
                    outputStream.write(("Content-Length:" + response.length + "\r\n").getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.write(response);
                    outputStream.flush();
                    log.info("----------end");

                } catch (Exception e) {
                    log.error("Exception:", e);
                }
            });
        }
    }
}
