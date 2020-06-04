package com.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Date 2020/6/4 17:48
 * @name AdminDemoApplication
 */


@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
public class AdminDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminDemoApplication.class,args);
    }
}
