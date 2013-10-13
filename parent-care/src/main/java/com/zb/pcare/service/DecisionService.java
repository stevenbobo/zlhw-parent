package com.zb.pcare.service;

import com.zb.pcare.dao.DecisionDao;
import com.zb.pcare.model.Decision;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-4
 * Time: 下午10:46
 * To change this template use File | Settings | File Templates.
 */
public class DecisionService {
    @Resource
    private DecisionDao decisionDao;
    @Resource
    private NoticeService noticeService;

    public void makeDecision(Decision decision){
        decision.setCreateDate(new Date());
        decisionDao.create(decision);
        noticeService.addJob(decision);
    }
}
