package com.demo;

import com.common.utils.DateTimeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * @Date 2020/5/16 10:28
 * @name Test
 */


public class Test {

    public static void main(String[] args) {

        BigDecimal amt = new BigDecimal(340.0810);
        amt = amt.setScale(2, RoundingMode.HALF_UP);
        System.out.println(">>>>" + amt);
        BigDecimal ab = new BigDecimal(340.08);
        ab = ab.setScale(2, RoundingMode.HALF_UP);
        if (amt.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(">>>>>小于0");
        }

        if (amt.compareTo(ab) == 0) {
            System.out.println("amt====大于");
        } else {
            System.out.println("amt====小于ab");
        }

    }

}
