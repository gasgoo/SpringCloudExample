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
        BigDecimal a=new BigDecimal(4);
        BigDecimal b=new BigDecimal(6);
        long day=2323;
        BigDecimal divide = BigDecimal.valueOf(day).divide(BigDecimal.valueOf(360), 6, RoundingMode.HALF_UP);
        System.out.println(divide);
        Date  st=new Date();
        System.out.println(DateTimeUtils.formatDateTime(st));
        Date end= DateTimeUtils.plusMonths(st,10);
        System.out.println(DateTimeUtils.formatDateTime(end));
        System.out.println(DateTimeUtils.offMonthBetweenDates(end,st));

    }
}
