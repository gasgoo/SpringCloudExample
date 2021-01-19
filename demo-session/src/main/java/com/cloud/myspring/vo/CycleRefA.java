package com.cloud.myspring.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Date 2020/12/28 20:47
 **/
@Component
@Lazy
public class CycleRefA {

    public CycleRefA() {
        System.out.println(">>>>>CycleRefA");
    }

    @Autowired
    private CycleRefB cycleRefB;

    public void print() {
        System.out.println(">>>>>CycleRefA test print");
    }
}
