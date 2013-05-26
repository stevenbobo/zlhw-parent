package com.zb.jnlxc.JBPM;

import java.util.Map;

import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.model.ProductTeam;
import com.zb.jnlxc.service.ProductRecordService;

public class StartProductRecordFlow implements ExternalActivityBehaviour{

	public void signal(ActivityExecution arg0, String arg1, Map<String, ?> arg2)
			throws Exception {
	}

	public void execute(ActivityExecution execution) throws Exception {
		ProductRecordService productRecordService=(ProductRecordService)BeanFactory.LookUp("productRecordService");
		Integer orderFormId = (Integer) execution.getVariable("orderFormId");
		productRecordService.startProductRecordFlow( orderFormId);
		
	}

}
