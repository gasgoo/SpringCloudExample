package com.cloud.config;

import com.cloud.session.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 */
@Configuration
public class SessionConfig {

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

    @Bean
    public SessionFilter sessionFilter(RedisTemplate redisTemplate) {
        SessionFilter sessionFilter = new SessionFilter();
        sessionFilter.setRedisTemplate(redisTemplate);
        return sessionFilter;
    }

}
