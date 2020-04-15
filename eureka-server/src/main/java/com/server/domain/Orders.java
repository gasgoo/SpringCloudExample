package com.server.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Date 2019/9/26 9:40
 */
@Getter
@Setter
public class Orders {

    private String code;
    private BigDecimal price;
    /**
     * 订单类型
     *   1  2  3
     **/
    private String type;

}
