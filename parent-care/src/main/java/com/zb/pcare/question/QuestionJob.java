package com.zb.pcare.question;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-4
 * Time: 下午10:40
 * To change this template use File | Settings | File Templates.
 */
public class QuestionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String questionDbId = (String)context.getJobDetail().getJobDataMap().get("questionDbId");
    }
}
