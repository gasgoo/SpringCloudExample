package com.common.service;

import com.alibaba.fastjson.JSON;
import com.common.OrderEnum;
import com.common.utils.AESUtil;
import com.demo.TreeMapDemo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Date 2019/10/24 10:35
 * @name BaseServiceTest
 */
public class BaseServiceTest {

    @Test
    public void encryptPhone() throws Exception {
        TreeMapDemo demo = new TreeMapDemo();
        demo.testComparable();
        String phone = "18101732978";
        String key = "2019_hello_world";
        String decrypt = AESUtil.encrypt(phone, key);
        System.out.println("加密>>>>>>" + decrypt);
        System.out.println("解密>>>>>>" + AESUtil.decrypt(decrypt, key));

        System.out.println(phone.replaceAll("(\\d{3})\\d{6}(\\d{2})", "$1****$2"));
        System.out.println(">>>>>" + phone.replace(phone.substring(3, 9), "****"));

    }

    @Test
    public void testSortEnum() {
        OrderEnum[] values = OrderEnum.values();
        List<OrderEnum> lists = Arrays.asList(values);
        List<OrderEnum> sortList = lists.stream().sorted(Comparator.comparing(OrderEnum::getIndex)).collect(Collectors.toList());
        System.out.println(">>>>" + JSON.toJSONString(sortList));

    }


}