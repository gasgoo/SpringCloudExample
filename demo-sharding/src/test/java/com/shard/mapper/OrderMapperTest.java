package com.shard.mapper;

import com.shard.ShardingApplication;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class OrderMapperTest extends TestCase {

    @Resource
    private OrderMapper orderMapper;

    public void testInsert() {
    }
}