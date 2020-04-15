package com.feign.web;

import com.feign.api.ApiService;
import com.feign.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author gg.rao
 * @Date 2019/4/2 19:45
 */
@RestController
public class FeignIndexController {

    @Autowired
    private ApiService apiService;

    @RequestMapping("/index")
    public String index(){
        System.out.println("====feign index");
        return apiService.index();
    }

    @RequestMapping("/test")
    public String test(){
        System.out.println("=====test");
        return "test";
    }
}


