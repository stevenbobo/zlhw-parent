package com.zb.jnlxc.JBPM;


import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.dao.MouldDAO;
import com.zb.jnlxc.model.Mould;

public class MouldFlowEndListener implements EventListener{
	Logger logger =LoggerFactory.getLogger(MouldFlowEndListener.class);
	 public void notify(EventListenerExecution execution) {
		 Integer mouldId=(Integer) execution.getVariable("mouldId");
		logger.info("模具流程:模具编号为{}的流程结束",execution.getKey());
	 }
}
