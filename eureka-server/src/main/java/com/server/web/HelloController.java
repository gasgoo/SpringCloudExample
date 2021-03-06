package com.server.web;

import com.server.annotation.WebLog;
import com.server.config.ApplicationProperties;
import com.server.domain.Orders;
import com.server.events.MyContextEventPublisher;
import com.server.message.MQProducer;
import com.server.message.MQRocketTemplateProducer;
import com.server.service.HandlerDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Author
 * @Date 2019/3/21 19:53
 */
@RestController
@RequestMapping("/api")
public class HelloController {
    private static Logger log = LoggerFactory.getLogger(HelloController.class);

    @Value("${server.port}")
    private int port;

    @Autowired
    private MQRocketTemplateProducer mqRocketTemplateProducer;
    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private HandlerDemo handlerDemo;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private MyContextEventPublisher myContextEventPublisher;
    @Autowired
    private ServerProperties serverProperties;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        log.info("feign test");
        return "Hello World!"+serverProperties.getPort();
    }


   @RequestMapping(value = "/msg",method = RequestMethod.GET)
    public String mqMsg(String msg){
        log.info("生产消息>>>>>>>>");
       mqProducer.send(Constants.topic,msg);
       return "ok";
   }

   @RequestMapping(value = "/async",method = RequestMethod.GET)
   @WebLog
    public String testAsync(String type,String code){
       log.info("主线程{}",Thread.currentThread().getName());
       Orders orders=new Orders();
       orders.setPrice(new BigDecimal(5000));
       orders.setCode(code);
       orders.setType(type);
       log.info("=========main over=======");
       handlerDemo.asyncHandle(orders);
       return "ok";
   }

   @RequestMapping(value = "/job",method = RequestMethod.GET)
    public String testJob(){
       String appKey = applicationProperties.getAliyun().getAppKey();
       log.info(">>>>>>config {}",appKey);
       return appKey;
   }

    @GetMapping(value = "/testEvent")
    public String testEvent() {
       log.info("Spring事件测试>>");
        myContextEventPublisher.happenEvent();
        return "ok";
   }



}
