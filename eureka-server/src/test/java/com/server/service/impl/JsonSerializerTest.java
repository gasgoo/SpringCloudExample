package com.server.service.impl;

import com.server.annotation.JsonSerializer;
import com.server.domain.Lock;
import org.junit.Test;

/**
 * @Date 2020/4/15 9:57
 * @name JsonSerializerTest
 */


public class JsonSerializerTest {

    @Test
    public void testJsonField() throws IllegalAccessException {
        Lock lock=new Lock("张三丰","纯阳无极功");
        lock.setCompany("武当山");
        lock.setProfessional("老道长");
        System.out.println(JsonSerializer.serialize(lock));
    }
}
