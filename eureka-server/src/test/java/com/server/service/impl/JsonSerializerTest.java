package com.server.service.impl;

import com.server.annotation.JsonSerializer;
import com.server.domain.Lock;
import com.server.domain.UserBean;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;

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

    @Test
    public void reflectionTest(){
        ObjectFactory objectFactory=new DefaultObjectFactory();
        UserBean user=objectFactory.create(UserBean.class);
        ObjectWrapperFactory factory=new DefaultObjectWrapperFactory();
        ReflectorFactory reflectorFactory=new DefaultReflectorFactory();
        MetaObject metaObject= MetaObject.forObject(user,objectFactory,factory,reflectorFactory);
        Reflector findClass=reflectorFactory.findForClass(UserBean.class);
        Constructor constructor=findClass.getDefaultConstructor();
        String[] gettableNames=findClass.getGetablePropertyNames();
        System.out.println(constructor.getName());
        System.out.println(Arrays.toString(gettableNames));
    }
}
