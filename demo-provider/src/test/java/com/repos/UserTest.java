package com.repos;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Date 2019/5/14 10:17
 */
@RunWith(SpringRunner.class)
public class UserTest {


    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    /**
     * @PerfTest(invocations = 300)：执行300次，和线程数量无关，默认值为1，表示执行1次；
     * @PerfTest(threads=30)：并发执行30个线程，默认值为1个线程；
     * @PerfTest(duration = 20000)：重复地执行测试至少执行20s。
     *
     * @Required(percentile99 = 10000)：要求99%的测试不超过10s;
     * @Date 2020/6/2 10:45
     **/
    @Test
    @PerfTest(invocations = 300,threads = 10,duration = 20000)
    @Required(percentile99 = 10000)
    public void test() {
        System.out.println("====" + Thread.currentThread().getName());
    }



}
