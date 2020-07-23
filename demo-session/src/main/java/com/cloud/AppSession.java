package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 *
 */
@SpringCloudApplication
public class AppSession {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AppSession.class, args);
    }
}
