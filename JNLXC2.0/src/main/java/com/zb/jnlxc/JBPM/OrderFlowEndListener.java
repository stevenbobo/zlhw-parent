package com.zb.jnlxc.JBPM;
import com.zb.util.Constants;
import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.dao.OrderFormDAO;
import com.zb.jnlxc.model.OrderForm;

public class OrderFlowEndListener implements EventListener{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	 public void notify(EventListenerExecution execution) {
		 Integer orderFormId=(Integer) execution.getVariable("orderFormId");
		 OrderFormDAO dao=(OrderFormDAO)BeanFactory.LookUp("orderFormDAO");
		 OrderForm orderForm=dao.loadById(orderFormId);
		 orderForm.setCurrentState(Constants.ORDER_IN_WAITING_PAICHAN);//订单流程结束
	     dao.update(orderForm);
		 logger.info("订单流程:订单编号为{}的流程结束 orderid={}",execution.getKey(),orderForm.getDbId());
	 }
}