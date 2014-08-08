package com.zb.jnlxc.JBPM;
import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.model.OrderForm;
import com.zb.jnlxc.model.PaiChanRecord;
import com.zb.util.Constants;
import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.factory.BeanFactory;

public class ProductRecordEndListener implements EventListener{
	Logger logger =LoggerFactory.getLogger(MouldFlowEndListener.class);

	 public void notify(EventListenerExecution execution) {
		 Integer paichanRecordId=(Integer) execution.getVariable("paichanRecordId");
         PaiChanRecordDAO dao=(PaiChanRecordDAO)BeanFactory.LookUp("paiChanRecordDAO");
         OrderFormDAO orderFormDAO=(OrderFormDAO)BeanFactory.LookUp("orderFormDAO");
         RemaindProductDAO remaindProductDAO = (RemaindProductDAO)BeanFactory.LookUp("remaindProductDAO");
		 PaiChanRecord paiChanRecord=dao.loadById(paichanRecordId);
         paiChanRecord.setCurrentState((byte)0);//设置排产结束
         OrderForm orderForm = paiChanRecord.getOrderForm();
         remaindProductDAO.storeRemainProduct(orderForm,paiChanRecord);
         orderFormDAO.updateCompState(orderForm);

		dao.update(paiChanRecord);
		logger.info("生产流程:生产记录号为{}的流程结束,排产编号:{}",execution.getKey(),paiChanRecord.getCode());
	 }
}