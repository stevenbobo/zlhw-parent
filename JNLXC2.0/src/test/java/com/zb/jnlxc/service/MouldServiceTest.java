package com.zb.jnlxc.service;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.model.AssociationCompany;
import org.jbpm.api.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.MouldTestRecord;
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class MouldServiceTest  extends AbstractTransactionalJUnit4SpringContextTests{
	@Resource
	MouldService mouldService;
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
	@Resource
	DepartDAO departDAO;
	@Resource
	private SchemeDAO schemeDAO;
    @Resource
    private AssociationCompanyDAO associationCompanyDAO;
	Admin admin;
	Mould mould;
    AssociationCompany company;
	@Before
	public void before() {
		admin=adminDAO.getById(1);
		mould=mouldDAO.getById(60061);
        associationCompanyDAO.getById(1);
	}
//	@Test
	public void testStartmouldFlowByKey(){
		Mould mould = new Mould();
		mould.setScheme(schemeDAO.getById(1));
		mould.setManufacture(company );
		mouldService.saveMould(mould);
		System.out.println(mould.getDbId());
	}
//	@Test
	public void testWaixieBuy(){
		List<Task>  tasks = flowService.getTaskService().findPersonalTasks(admin.getAccount());
		Task task = tasks.get(0);
		mouldService.waixieBuy(task.getId(), "",admin);
	}
	
//	@Test
	public void testShoumu() {
		List<Task>  tasks = flowService.getTaskService().findGroupTasks(admin.getAccount());
		Task task = tasks.get(0);
		mouldService.shoumu(task.getId(), "",admin);
	}

//	@Test
	public void testTestModelRecord() {
		List<Task>  tasks = flowService.getTaskService().findGroupTasks(admin.getAccount());
		Task task = tasks.get(0);
		MouldTestRecord mouldTestRecord = new MouldTestRecord();
		mouldTestRecord.setConclusion("OK");
		mouldTestRecord.setCreateTime(new Date());
		mouldTestRecord.setMould(mould);
		mouldTestRecord.setQualityTester(admin);
		mouldTestRecord.setRepairman(admin);
		mouldTestRecord.setUseStickNum(10);
		mouldService.testModelRecord(task.getId(), mouldTestRecord,"",admin);
	}

//	@Test
	public void testDeside() {
		List<Task>  tasks = flowService.getTaskService().findGroupTasks(admin.getAccount());
		Task task = tasks.get(0);
		mouldService.deside(task.getId(), "", "成功","",admin);
	}
	
	@Test
	public void testFindAll() {
		mouldService.findHql("from Mould order by dbid desc", 0, 10);
	}

    public void autoGenerate(){
        String path1="D:\\workspaces\\services\\zlhw-parent\\JNLXC2.0\\src\\main\\webapp\\content\\mouldprocess";
        String path2="D:\\workspaces\\services\\zlhw-parent\\JNLXC2.0\\src\\main\\webapp\\content\\newmould";
        File file1 = new File(path1);
        File file2 = new File(path2);

    }

}
