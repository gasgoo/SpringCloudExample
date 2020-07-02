package com.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Date 2020/7/2 10:44
 * @name WebMvcConfig
 */


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public void addViewController(ViewControllerRegistry registry) {
        registry.addViewController("/chatroom").setViewName("/wechat_room");
    }
}
