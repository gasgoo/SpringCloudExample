package com.feign.api;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author gg.rao
 * @Date 2019/4/2 19:42
 */
@Component
public class ApiServiceError implements ApiService {

    @Override
    public String index() {
        return "Server error!";
    }




}
