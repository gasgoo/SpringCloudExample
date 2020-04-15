package com.server.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Date 2019/10/8 11:26
 */
@Slf4j
public class QuartzJobTest implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("<<<<<<QuartzJobTest>>>>>");

    }
}
