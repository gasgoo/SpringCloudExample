package com.demo;

import com.common.utils.DateTimeUtils;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Date 2020/5/16 10:28
 * @name Test
 */


public class Test {

    public static void main(String[] args) {

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

        LocalDate analyseStartTime = LocalDate.now();
        Date startDate = DateTimeUtils.asUtilDate(analyseStartTime);
        DateTimeUtils.formatDateTime(startDate);
        System.out.println("====" + startDate);


    }

}
