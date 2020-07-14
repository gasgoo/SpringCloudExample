package com.server.service.impl;

import com.common.utils.JSONUtil;
import com.server.annotation.JsonSerializer;
import com.server.domain.LockDTO;
import com.server.domain.UserBean;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date 2020/4/15 9:57
 * @name JsonSerializerTest
 */


public class JsonSerializerTest {

    @Test
    public void testJsonField() throws IllegalAccessException {
        LockDTO lockDTO = new LockDTO("张三丰", "纯阳无极功");
        lockDTO.setCompany("武当山");
        lockDTO.setProfessional("老道长");
        System.out.println(JsonSerializer.serialize(lockDTO));
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

    @Test
    public void test() {
        String json = "{'Server': 'Tengine/1.5.2', 'Date': 'Tue, 14 Jul 2020 07:20:37 GMT', 'Content-Type': 'application/json;charset=UTF-8', 'Transfer-Encoding': 'chunked', 'Connection': 'keep-alive', 'Access-Control-Allow-Credentials': 'true', 'Set-Cookie': 'token=\"vJfVcCxwYEVYYoElJxaWcBZ5xSCu/589ICh4oK2NyAr6iv5V5KT6Ag==\"; Version=1; Domain=uc56.com; Path=/; HttpOnly, yhEmpCode=\"\"; Domain=uc56.com; Path=/; HttpOnly, compCode=\"\"; Domain=uc56.com; Path=/; HttpOnly, userAccountType=Portal; Domain=uc56.com; Path=/; HttpOnly, userName=019630; Domain=uc56.com; Path=/; HttpOnly, empCode=019630; Domain=uc56.com; Path=/; HttpOnly, empName=%E5%AE%8B%E6%B6%9B; Domain=uc56.com; Path=/; HttpOnly, updPwdFlag=true; Domain=uc56.com; Path=/; HttpOnly, updPwdIntervalDay=79; Domain=uc56.com; Path=/; HttpOnly, omgOrgId=1111; Domain=uc56.com; Path=/; HttpOnly, encryptOrgId=\"MTExMQ==\"; Version=1; Domain=uc56.com; Path=/; HttpOnly, cmsOrgId=10000000; Domain=uc56.com; Path=/; HttpOnly, cmsOrgIdStr=10000000#10046341#10046936#88830541#10000195; Domain=uc56.com; Path=/; HttpOnly, portalIndex=portal; Domain=uc56.com; Path=/; HttpOnly, lastLoginTime=1594710961000; Domain=uc56.com; Path=/; HttpOnly'}";

        String pattern = "//d";

        boolean isMatch = Pattern.matches(pattern, json);
        System.out.println("字符串中是否包含:" + isMatch);
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(json);
        List<String> result = Lists.newArrayList();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        System.out.println(JSONUtil.toJson(result));

    }
}
