package com.common.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Desc
 * @Date 2021/2/8 10:48
 **/
@Getter
@Setter
public class Loan {

    private Long id;

    private BigDecimal amountA;

    private BigDecimal amountB;

    private BigDecimal amountC;

    private BigDecimal paidAmountA = BigDecimal.ZERO;

    private BigDecimal paidAmountB = BigDecimal.ZERO;

    private BigDecimal paidAmountC = BigDecimal.ZERO;

    private Boolean flag;


}
