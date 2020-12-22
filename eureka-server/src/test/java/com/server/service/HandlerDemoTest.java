package com.server.service;

import com.common.utils.AESUtil;
import com.server.EurekaDemoApplication;
import com.server.domain.Orders;
import com.server.message.MQProducer;
import com.server.message.MQRocketTemplateProducer;
import com.server.web.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Date 2019/9/26 10:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaDemoApplication.class)
public class HandlerDemoTest {

    @Autowired
    private HandlerDemo handlerDemo;
    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private MQRocketTemplateProducer mqRocketTemplateProducer;

    @Test
    public void handle() {
        Orders orders=new Orders();
        orders.setCode("O11111");
        orders.setPrice(new BigDecimal(500));
        orders.setType("2");
        handlerDemo.handle(orders);
        handlerDemo.testMap();
    }

    @Test
    public void testMQSend(){
        mqProducer.send(Constants.topic,"MSG TEST");
    }

    @Test
    public void testMqDefaultProducer(){
        mqProducer.send(Constants.topic,"MSG TEST 消息内容1");
        String msg="Topic-test:pushTag2".concat("--pushTag2-消息发送测试!");
        mqRocketTemplateProducer.sendMsg(msg);
    }


}