package com.zb.pcare.service;

import java.util.Date;

import com.zb.pcare.model.Decision;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronTriggerExample
{
    public void run()
            throws Exception
    {
        Logger log = LoggerFactory.getLogger(CronTriggerExample.class);

        log.info("------- Initializing -------------------");

        SchedulerFactory sf = new StdSchedulerFactory("conf/develop/quartz.properties");
        Scheduler sched = sf.getScheduler(); // 初始化调度器

        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");

        JobDetail job = JobBuilder.newJob(DecisionJob.class).withIdentity("job1", "group1").build(); // 设置作业，具体操作在DecisionJob类里
        job.getJobDataMap().put("decision",new Decision());
        CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build(); // 设置触发器 每20秒执行一次

        Date ft = sched.scheduleJob(job, trigger); // 设置调度作业
        log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());

        job = JobBuilder.newJob(DecisionJob.class).withIdentity("job2", "group1").build();

        trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").withSchedule(CronScheduleBuilder.cronSchedule("15 0/2 * * * ?")).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());

        job = JobBuilder.newJob(DecisionJob.class).withIdentity("job3", "group1").build();

        trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger3", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?")).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());

        job = JobBuilder.newJob(DecisionJob.class).withIdentity("job4", "group1").build();

        trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger4", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 17-23 * * ?")).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());

        job = JobBuilder.newJob(DecisionJob.class).withIdentity("job5", "group1").build();

        trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger5", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0 0 10am 1,15 * ?")).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());

        job = JobBuilder.newJob(DecisionJob.class).withIdentity("job6", "group1").build();

        trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger6", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * ? * MON-FRI")).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());

        job = JobBuilder.newJob(DecisionJob.class).withIdentity("job7", "group1").build();

        trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity("trigger7", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * ? * SAT,SUN")).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());

        log.info("------- Starting Scheduler ----------------");

        sched.start(); // 开启调度任务，执行作业

        log.info("------- Started Scheduler -----------------");

        log.info("------- Waiting five minutes... ------------");
        try
        {
            Thread.sleep(300000L); // 线程休眠5分钟，任务还继续执行
        }
        catch (Exception e)
        {
        }

        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true); // 终止任务

        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args)
            throws Exception
    {
        CronTriggerExample example = new CronTriggerExample();
        example.run();
    }
}
