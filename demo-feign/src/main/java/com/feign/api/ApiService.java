package com.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feign使用例子
 * @Author gg.rao
 * @Date 2019/4/1 20:57
 */
@FeignClient(value = "EUREKASERVER",fallback = ApiServiceError.class)
public interface ApiService {

        @RequestMapping(value = "/api/hello",method = RequestMethod.GET)
        public String index();

    }

