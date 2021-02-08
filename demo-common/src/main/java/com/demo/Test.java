package com.demo;

import com.alibaba.fastjson.JSON;
import com.common.model.Loan;
import com.common.utils.DateTimeUtils;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Date 2020/5/16 10:28
 * @name Test
 */


public class Test {

    public static void main(String[] args) {

        List<Loan> loanLists = Lists.newArrayList();
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setAmountA(new BigDecimal("400"));
        loan.setAmountB(new BigDecimal("30"));
        loan.setAmountC(new BigDecimal("2.9"));
        loanLists.add(loan);

        Loan loan1 = new Loan();
        loan1.setId(2L);
        loan1.setAmountA(new BigDecimal("400"));
        loan1.setAmountB(new BigDecimal("32"));
        loan1.setAmountC(new BigDecimal("2.7"));
        loanLists.add(loan1);

        Loan loan2 = new Loan();
        loan2.setId(3L);
        loan2.setAmountA(new BigDecimal("400"));
        loan2.setAmountB(new BigDecimal("32"));
        loan2.setAmountC(new BigDecimal("2.7"));
        //loanLists.add(loan2);
        BigDecimal payAmount = new BigDecimal("500");

        computeRealPay(loanLists, payAmount);

        System.out.println(">>>>>" + JSON.toJSONString(loanLists));

    }

    private static void computeRealPay(List<Loan> loanLists, BigDecimal payAmount) {
        for (int i = 0; i < loanLists.size(); i++) {
            Loan temp = loanLists.get(i);
            if (payAmount.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }
            BigDecimal subtractAmtA = payAmount.subtract(temp.getAmountA());
            if (subtractAmtA.compareTo(BigDecimal.ZERO) > 0) {
                temp.setPaidAmountA(temp.getAmountA());
                BigDecimal subtractAmtB = subtractAmtA.subtract(temp.getAmountB());
                if (subtractAmtB.compareTo(BigDecimal.ZERO) > 0) {
                    temp.setPaidAmountB(temp.getAmountB());
                    BigDecimal subtractAmtC = subtractAmtB.subtract(temp.getAmountC());
                    if (subtractAmtC.compareTo(BigDecimal.ZERO) > 0) {
                        temp.setPaidAmountC(temp.getAmountC());
                        temp.setFlag(true);
                        payAmount = subtractAmtC;
                        System.out.println(">>>>>>剩余金额:" + subtractAmtC);
                    } else {
                        temp.setPaidAmountC(subtractAmtC);
                        payAmount = BigDecimal.ZERO;
                        temp.setFlag(false);
                    }
                } else {
                    temp.setPaidAmountB(subtractAmtB);
                    payAmount = BigDecimal.ZERO;
                    temp.setFlag(false);
                }
            } else {
                temp.setPaidAmountA(payAmount);
                payAmount = BigDecimal.ZERO;
                temp.setFlag(false);
            }
        }
    }

}
