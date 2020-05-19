package com.common.utils;

public class SnowFlakeUtil {

    /**
     * SnowFlake算法（数据中心）标识
     */
    private static final Long DATA_CENTER_ID = 1L;

    /**
     * SnowFlake算法（机器标识小于31大于0）标识
     */
    private static final Long MACHINE_ID = 8L;

    private static SnowFlake snowFlake = new SnowFlake(DATA_CENTER_ID, MACHINE_ID);

    public static Long nextId() {
        return snowFlake.nextId();
    }
}
