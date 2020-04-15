package com.feign.api;

import com.feign.FeignDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author gg.rao
 * @Date 2019/4/1 21:12
 */
@SpringBootTest(classes = FeignDemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApiServiceTest {

    @Autowired
    private ApiService apiService;
    @Test
    public void index() {
        System.out.println("feign=========="+apiService.index());
    }
}