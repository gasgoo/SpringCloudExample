package com.server;

import com.server.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.concurrent.CountDownLatch;

@SpringCloudApplication
@EnableConfigurationProperties({ApplicationProperties.class})
@EnableEurekaServer
@EnableCaching
@EnableScheduling
public class EurekaDemoApplication extends SpringBootServletInitializer {

    private static final Logger log= LoggerFactory.getLogger(EurekaDemoApplication.class);


    /*exclude 排除默认的数据源*/
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context=SpringApplication.run(EurekaDemoApplication.class, args);

       /* StringRedisTemplate redisTemplate=context.getBean(StringRedisTemplate.class);
        CountDownLatch latch=context.getBean(CountDownLatch.class);
        log.info("发送消息======");
        redisTemplate.convertAndSend("testTopic","Hello redis Message");
        //线程阻塞 等待消息接收后计数器值=0唤醒
        latch.await();*/

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EurekaDemoApplication.class);
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        /** 设置视图路径的前缀 */
        resolver.setPrefix("/WEB-INF/jsp/");
        /** 设置视图路径的后缀 */
        resolver.setSuffix(".jsp");
        return resolver;	}


    


}
