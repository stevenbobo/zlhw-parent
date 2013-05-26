package com.zb.jnlxc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.zb.jnlxc.dao.*;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.MouldRecord;
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class PaiDanFlowServiceTest  extends AbstractTransactionalJUnit4SpringContextTests{
	Logger logger = LoggerFactory.getLogger(PaiDanFlowServiceTest.class);
	@Resource
	PaiDanFlowService paiDanFlowService;
	@Resource
	FlowService flowService;
	@Resource
	MouldDAO mouldDAO;
	@Resource
	MouldTestRecordDAO mouldTestRecordDAO;
	@Resource
	AdminDAO adminDAO;
	String id ="1_2";
	int mould_dbid=1;
	@Resource
	DepartDAO departDAO;
	@Resource
    DataDictDAO materialDAO;
	ProductTeamDAO productTeamDAO;
	Admin admin;
	@Before
	public void before() {
		admin=adminDAO.getById(1);
	}
	
	public void testFlow(){
		testStartmouldFlowByKey();
		testDistribution();
		testRecord();
		testDanhua();
	}
	
//	@Test
	public void testStartmouldFlowByKey() {
		Map map = new HashMap();
		map.put("mouldId", mould_dbid);
		ProcessInstance processInstance = paiDanFlowService.startmouldFlowByKey(id, map);
		logger.info("--------------------"+processInstance.getId());
	}

//	@Test
	public void testDistribution() {
		List<Task>  tasks = flowService.getTaskService().findGroupTasks(admin.getAccount());
		Task task = tasks.get(0);
		paiDanFlowService.distribution(task.getId(), mould_dbid,"",admin);
	}
	

//	@Test
	public void testRecord() {
		List<Task>  tasks = flowService.getTaskService().findPersonalTasks("zqh");
		Task task = tasks.get(0);
		MouldRecord mouldRecord = new MouldRecord();
		mouldRecord.setUnloadingReason("OK");
		mouldRecord.setCreateTime(new Date());
		mouldRecord.setProductTeam(productTeamDAO.getById(1));
		mouldRecord.setMould(mouldDAO.getById(1));
		mouldRecord.setOperator(admin);
		mouldRecord.setMaterial(materialDAO.getById(1));
		mouldRecord.setHeatingTime("111");
		Set<String> set=flowService.getTaskService().getOutcomes(task.getId());
		paiDanFlowService.record(task.getId(), mouldRecord, "氮化","",admin);
	}

//	@Test
	public void testDanhua() {
		List<Task>  tasks = flowService.getTaskService().findGroupTasks("zqh");
		Task task = tasks.get(0);
		paiDanFlowService.danhua(task.getId(),"",admin);
	}

//	@Test
	public void testPanding() {
		List<Task>  tasks = flowService.getTaskService().findGroupTasks("zqh");
		Task task = tasks.get(0);
		Mould mould = mouldDAO.getById(1);
		paiDanFlowService.panding(task.getId(), "正常入库","","",admin);
	}

//	@Test
	public void testStorage() {
		List<Task>  tasks = flowService.getTaskService().findGroupTasks("zqh");
		Task task = tasks.get(0);
		paiDanFlowService.storage(task.getId(),"",admin);
	}

}
