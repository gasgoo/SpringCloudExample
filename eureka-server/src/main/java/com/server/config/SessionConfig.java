package com.server.config;

import com.cloud.session.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 */
@Configuration
public class SessionConfig {

    /**
     * @Description 配置filter生效
     * @Date 2020/7/23 15:47
     **/
    @Bean
    public FilterRegistrationBean sessionFilterRegistration(SessionFilter sessionFilter) {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        registration.setOrder(1);
        return registration;
    }

    //定义过滤器组件
    @Bean
    public SessionFilter sessionFilter(RedisTemplate redisTemplate) {
        SessionFilter sessionFilter = new SessionFilter();
        sessionFilter.setRedisTemplate(redisTemplate);
        return sessionFilter;
    }

}
