package com.cloud.myspring.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Date 2020/12/28 20:48
 **/

@Component
@Lazy
public class CycleRefB {

    public CycleRefB() {
        System.out.println(">>>>>CycleRefB");
    }

    @Autowired
    private CycleRefB cycleRefB;

    public void print() {
        System.out.println(">>>>>CycleRefB test print");
    }
}
