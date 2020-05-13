package com.server.config;

import com.server.job.MyFirstJob;
import com.server.job.QuartzJobTest;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务quartz配置
 * @Date 2019/10/8 10:46
 */
@Configuration
public class QuartzConfig {

    //使用jobDetail包装job
    @Bean
    public  JobDetail MyFirstJob(){
        return JobBuilder.newJob(MyFirstJob.class).withIdentity("myFirstJob").storeDurably().build();
    }

    //把jobDetail注册到trigger中去15秒执行一次
    @Bean
    public Trigger myFirstTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(5).repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(MyFirstJob())
                .withIdentity("myJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }


   // @Bean
    public  JobDetail QuartzJobTestJob(){
        return JobBuilder.newJob(QuartzJobTest.class).withIdentity("quartzJobTest").storeDurably().build();
    }
    //可以把cron放在配置中动态读取
    //@Bean
    public Trigger quartzTrigger2(){
        return TriggerBuilder.newTrigger().forJob(QuartzJobTestJob())
                .withIdentity("Trigger2")
                .withSchedule(CronScheduleBuilder.cronSchedule("* 0/5 * * * ?"))
                .build();

    }

}
