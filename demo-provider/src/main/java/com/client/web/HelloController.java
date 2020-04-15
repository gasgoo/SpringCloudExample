package com.client.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author gg.rao
 * @Date 2019/3/21 19:53
 */
@RestController
public class HelloController {
    private static Logger log= LoggerFactory.getLogger(HelloController.class);

    @Value("${server.port}")
    private int port;


        @RequestMapping("hello")
        String hello() {
            log.info("log working........");
            return "Hello World!"+port;
        }


}
