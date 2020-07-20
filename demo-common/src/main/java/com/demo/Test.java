package com.demo;

import com.common.utils.DateTimeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @Date 2020/5/16 10:28
 * @name Test
 */


public class Test {

    public static void main(String[] args) {
        String s1 = new String("1");
        String s2 = "1";
        s1.intern();
        System.out.println(s1 == s2);

        String s3 = new String("1") + new String("1");
        String s4 = "11";
        s3.intern();
        System.out.println(s3 == s4);
        System.out.println(s3.equals(s4));

        System.out.println("==========================abc");
        String str1="abc";
        String str2=new String("abc");
        String str3=str2.intern();
        System.out.println(str1==str2);
        System.out.println(str2==str3);
        System.out.println(str1==str3);

        System.out.println(str1.equals(str2));
        System.out.println(str2.equals(str3));
        System.out.println(str1.equals(str3));



    }

}
