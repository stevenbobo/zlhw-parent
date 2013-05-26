package com.zb.jnlxc.JBPM;
import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.dao.ProductRecordDAO;
import com.zb.jnlxc.model.ProductRecord;

public class ProductRecordEndListener implements EventListener{
	Logger logger =LoggerFactory.getLogger(MouldFlowEndListener.class);

	 public void notify(EventListenerExecution execution) {
		 Integer productRecordId=(Integer) execution.getVariable("productRecordId");
		 ProductRecordDAO dao=(ProductRecordDAO)BeanFactory.LookUp("productRecordDAO");
		 ProductRecord productRecord=dao.getById(productRecordId);
		 productRecord.setCurrentState((byte)0);//设置图纸为正常
		 try {
			dao.update(productRecord);
		} catch (BaseErrorModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("生产流程:生产记录号为{}的流程结束",execution.getKey());
	 }
}