package com.common.service;

import com.common.utils.AESUtil;
import org.junit.Test;



/**
 * @Date 2019/10/24 10:35
 * @name BaseServiceTest
 */
public class BaseServiceTest {

    @Test
    public void encryptPhone() throws Exception {
        String phone="18101732978";
        String key="2019_hello_world";
        String decrypt = AESUtil.encrypt(phone, key);
        System.out.println("加密>>>>>>"+decrypt);
        System.out.println("解密>>>>>>"+AESUtil.decrypt(decrypt,key));

        System.out.println(phone.replaceAll("(\\d{3})\\d{6}(\\d{2})", "$1****$2"));
        System.out.println(">>>>>"+phone.replace(phone.substring(3,9),"****"));

    }


}