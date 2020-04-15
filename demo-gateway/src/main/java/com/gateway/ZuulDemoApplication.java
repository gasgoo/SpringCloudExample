package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 网关服务启动  开启zipkin追踪
 * @Date 2019/3/30 19:38
 */

@SpringCloudApplication
public class ZuulDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulDemoApplication.class,args);
    }
}
