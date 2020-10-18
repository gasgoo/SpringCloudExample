package com.shard.service;

import com.shard.domain.Order;
import com.shard.domain.UserDO;
import com.shard.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc
 * @Date 2020/9/1 20:13
 **/

@Service
@Transactional
public class UserService {

    @Resource
    public UserMapper userMapper;

    @Resource
    public OrderService orderService;

    public boolean insert(UserDO u) {
        return userMapper.insert(u) > 0 ? true : false;
    }

    public List<UserDO> findAll() {
        return userMapper.findAll();
    }

    public List<UserDO> findByUserIds(List<Integer> ids) {
        return userMapper.findByUserIds(ids);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionTestSucess() {
        UserDO u = new UserDO();
        u.setUserId(21L);
        u.setAge(25);
        u.setName("war3 1.22");
        userMapper.insert(u);

        Order order = new Order();
        order.setUserId(21L);
        order.setOrderId(21L);
        order.setUserName("userName22");
        order.setPassWord("password22");
        orderService.insert(order);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionTestFailure() throws IllegalAccessException {
        UserDO u = new UserDO();
        u.setUserId(13L);
        u.setAge(25);
        u.setName("war3 1.27 good");
        userMapper.insert(u);

        Order order = new Order();
        order.setUserId(21L);
        order.setOrderId(21L);
        order.setUserName("userName21");
        order.setPassWord("password21");
        orderService.insert(order);
        throw new IllegalAccessException();
    }

}
