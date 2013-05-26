package com.zb.jnlxc.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ZLHW.base.Form.Page;
import com.zb.jnlxc.dao.AdminDAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.MyTask;
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class TestFlowServiceTest extends AbstractTransactionalJUnit4SpringContextTests  {
	@Resource
	FlowService flowService;
	@Resource
	AdminDAO adminDAO;
	Admin admin;
	
//	@Before
	public void before() {
		admin=adminDAO.getById(1);
	}
//	@Test
	public void startTestFlow(){
		flowService.startProcessInstanceByKey("test", "111");
	}
//	@Test
	public void testStep1(){
//		Page page = new Page();
//		page.setQueryCondition(new HashMap());
//		flowService.findAllTask(admin,page);
//		List<MyTask> tasks = page.getResultData();
//		MyTask task = tasks.get(0);
//		System.out.println(task.getId());
		List<Task>  tasks = flowService.getTaskService().findPersonalTasks("zqh");
		Task task = tasks.get(0);
		flowService.completeTask(task.getId(),admin);
	}
	
//	@Test
	public void testStep2() throws ParseException {
		Page page = new Page();
		page.setQueryCondition(new HashMap());
		flowService.loadTask(admin, page);
		List<MyTask> tasks = page.getResultData();
		MyTask task = tasks.get(0);
		flowService.completeTask(task.getId(),admin);
	}

}
