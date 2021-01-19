package com.server.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 应用注册 拦截器
 * @Author gg.rao
 * @Date 2019/3/28 19:36
 */
@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        //registry.addInterceptor(new ApiInterceptor());
        //registry.addInterceptor(new DempotentInterceptor());
    }


    /*配置跨域支持  解决非简单请求的跨域问题的本质是服务器添加一些响应头信息
     * 解决简单请求跨域问题是 响应头中添加 Origin属性
     * 也可以用 @CrossOrigin 注解在某个类或方法上支持跨域*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)//是否允许发送Cookie
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(3600 * 24);//用来指定本次预检请求的有效期，单位为秒
    }

}
