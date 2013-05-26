package com.zb.jnlxc.JBPM;
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
		 OrderForm orderForm=dao.getById(orderFormId);
		 orderForm.setCurrentState((byte)0);//设置图纸为正常
		 try {
			dao.update(orderForm);
		} catch (BaseErrorModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("订单流程:订单编号为{}的流程结束",execution.getKey());
	 }
}