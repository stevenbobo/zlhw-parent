package com.zb.jnlxc.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.OrderFormDAO;
import com.zb.jnlxc.model.OrderForm;

@Transactional
@Service
public class OrderFormService extends BaseService<OrderFormDAO,OrderForm, Integer> {
	private static final Log log = LogFactory.getLog(OrderFormService.class);
	@Resource
	private FlowService flowService;

	/**
	 * 发布一个订单流程图(流程版本，只发布一次，平常不用)
	 * 
	 * 流程图jpdl位置（比如"./com/zb/JBPM/orderFlow.jpdl.xml"）
	 * @return
	 */
	public String deployFlow() {
		String path = "./com/zb/JBPM/orderFlow.jpdl.xml";
		return flowService.getRepositoryService().createDeployment()
				.addResourceFromClasspath(path).deploy();
	}

	/**
	 * 开启订单流程
	 * 
	 * @param id
	 *            模具订单号
	 * @return
	 */
	public ProcessInstance startOrderFlowByKey(String id) {
		return flowService.startProcessInstanceByKey("orderFlow", id);
	}

	/**
	 * 开启订单流程
	 * 
	 * @param id
	 *            模具订单号
	 * @return
	 */
	public ProcessInstance startOrderFlowByKey(String id, Map map) {
		return flowService.startProcessInstanceByKey("orderFlow", id, map);
	}

	/**
	 * 找到当前的流程实例
	 * 
	 * @param id
	 * @return
	 */
	public ProcessInstance findProcessInstanceByKey(String id) {
		return flowService.findProcessInstanceByKey("orderFlow", id);
	}

	

}
