package com.zb.pcare.service;

import java.util.Date;

import com.zb.pcare.model.Decision;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecisionJob implements Job
{
//    private Decision decision;
    private static Logger _log = LoggerFactory.getLogger(DecisionJob.class);

//    public DecisionJob(Decision decision){
//        this.decision = decision;
//    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException // 具体的作业
    {
        Decision decison = (Decision)context.getJobDetail().getJobDataMap().get("decision");
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
        System.out.println("发出通知:");
    }
}