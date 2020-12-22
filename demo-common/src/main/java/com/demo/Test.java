package com.demo;

import com.common.utils.DateTimeUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * @Date 2020/5/16 10:28
 * @name Test
 */


public class Test {

    public static void main(String[] args) {

        BigDecimal amt = new BigDecimal("5000");
        BigDecimal ab = new BigDecimal("4500");
        if (amt.compareTo(ab) < 0) {
            System.out.println("amt====大于");
        } else {
            System.out.println("amt====小于ab");
        }

    }

}
