package com.feign.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @TODO
 * @Date 2019/6/13 17:10
 */
@Service
public class TestService {

    @Autowired
    private ApiService apiService;

    public String testfegin(){
        System.out.println("===testService");
        return apiService.index();
    }
}
