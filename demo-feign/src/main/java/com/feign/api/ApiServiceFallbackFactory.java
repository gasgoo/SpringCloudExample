package com.feign.api;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *  feign降级接口类 可以获取具体的异常信息
 * @Date 2020/6/2 16:43
 * @name ApiServiceFallbackFactory
 */


@Slf4j
@Component
public class ApiServiceFallbackFactory implements FallbackFactory<ApiService> {
    @Override
    public ApiService create(Throwable throwable) {
        if(throwable==null) {
            return null;
        }
        final String msg=throwable.getMessage();
        log.info("========ApiService降级:"+msg);
        return new ApiService() {
            @Override
            public String index() {
                log.info("Exception========ApiService降级:"+msg);
                return msg;
            }
        };
    }
}
