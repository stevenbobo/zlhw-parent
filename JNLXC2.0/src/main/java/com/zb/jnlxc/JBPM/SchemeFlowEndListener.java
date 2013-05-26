package com.zb.jnlxc.JBPM;


import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.dao.SchemeDAO;
import com.zb.jnlxc.model.Scheme;

public class SchemeFlowEndListener implements EventListener{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	 public void notify(EventListenerExecution execution) {
		 SchemeDAO dao=(SchemeDAO)BeanFactory.LookUp("schemeDAO");
		 Integer schemeId=(Integer)execution.getVariable("schemeId");
		 Scheme scheme=dao.getById(schemeId);
		 scheme.setCurrentState((byte)0);//设置图纸为正常
		 try {
			dao.update(scheme);
		} catch (BaseErrorModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("图纸流程:图纸编号为{}流程结束",execution.getKey());
	 }
}
