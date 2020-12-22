package com.shard.mapper;

import com.alibaba.fastjson.JSON;
import com.shard.ShardingApplication;
import com.shard.domain.Order;
import com.shard.domain.UserDO;
import com.shard.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class OrderMapperTest extends TestCase {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserService userService;

    @Test
    public void testSelectByOrderId() {
        Order order = orderMapper.selectByOrderId(21L);
        System.out.println(JSON.toJSONString(order));
    }

    @Test
    public void testFindAll() {
        List<Order> all = orderMapper.findAll();
        System.out.println(">>>>>size:" + all.size() + JSON.toJSONString(all));
    }

    @Test
    public void testUserInsertSuccess() {
        userService.transactionTestSucess();
    }


    @Test
    public void testUserInsertFail() throws IllegalAccessException {
        userService.transactionTestFailure();
    }
    @Test
    public void testUserInsert() {
        UserDO userDO=new UserDO();
        userDO.setAge(29);
        userDO.setId(29L);
        userDO.setUserId(29L);
        userDO.setName("29name");
        userService.insert(userDO);
        Order order=new Order();
        order.setOrderId(29L);
        order.setUserId(29L);
        order.setUserName("29name");
        order.setPassWord("29Pwd");
        orderMapper.insert(order);
    }


}