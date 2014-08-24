package com.zb.jnlxc.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通知任务服务
 * @author zhengbo.zb
 * @version $Id: NotifyFlowService.java, v 0.1 2014年8月21日 上午11:18:55 zhengbo.zb Exp $
 */
@Transactional
@Service
public class NotifyFlowService {
    private static final Logger logger = LoggerFactory.getLogger(MouldService.class);

    @Resource
    FlowService flowService;
    
    public ProcessInstance startNotify(String notifyId ,String senderAccount, String reciverAccount,
    		String reciverGroupName,String vmform,String remark){
        logger.info("开启通知.notifyId={}:",notifyId);
        Map map = new HashMap();
        map.put("senderAccount",senderAccount);
        map.put("remark",remark);
        map.put("reciverAccount", reciverAccount);
        map.put("reciverGroupName", reciverGroupName);
        map.put("vmform", vmform);
        return flowService.startProcessInstanceByKey("notify",notifyId,map);
    }

}
