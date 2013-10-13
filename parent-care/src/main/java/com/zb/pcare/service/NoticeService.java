package com.zb.pcare.service;

import com.zb.pcare.model.Decision;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class NoticeService {
    Logger logger = LoggerFactory.getLogger(NoticeService.class);
    SchedulerFactory sf;
    Scheduler sched;
    @PostConstruct
    public void init() throws SchedulerException {
        sf = new StdSchedulerFactory();
        sched = sf.getScheduler(); // 初始化调度器
    }

    public void addJob(Decision decision)  {
        JobDetail job = JobBuilder.newJob(DecisionJob.class).withIdentity("job"+decision.getDbId(), decision.getUser().getDbId()+"").build();
        job.getJobDataMap().put("decision",decision);
        CronTrigger trigger =TriggerBuilder.newTrigger().withIdentity("trigger"+decision.getDbId(), decision.getUser().getDbId()+"").withSchedule(CronScheduleBuilder.cronSchedule(decision.getTriggerRule())).build();
        try {
            sched.scheduleJob(job, trigger); // 设置调度作业
        } catch (SchedulerException e) {
            logger.error("设置调度任务失败"+decision.toString(),e);
        }
    }
}