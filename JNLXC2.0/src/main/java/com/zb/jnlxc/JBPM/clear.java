package com.zb.jnlxc.JBPM;

import java.util.List;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.junit.Test;

import com.ZLHW.base.factory.BeanFactory;

public class clear {
	ProcessEngine processEngine = (ProcessEngine)BeanFactory.LookUp("processEngine");
	RepositoryService repositoryService = processEngine.getRepositoryService();
	ExecutionService executionService = processEngine.getExecutionService();
	TaskService taskService = processEngine.getTaskService();
	
	public void test(){
		List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition pd : pdList) {
			repositoryService.deleteDeploymentCascade(pd.getDeploymentId());
		}
		deploy();
	}
	@Test
	public void deploy(){
//		repositoryService.createDeployment().addResourceFromClasspath(
//		"./com/zb/jnlxc/JBPM/mouldFlow.jpdl.xml").deploy();
		repositoryService.createDeployment().addResourceFromClasspath(
		"./com/zb/jnlxc/JBPM/orderFlow.jpdl.xml").deploy();
		repositoryService.createDeployment().addResourceFromClasspath(
		"./com/zb/jnlxc/JBPM/productRecordFlow.jpdl.xml").deploy();
//		repositoryService.createDeployment().addResourceFromClasspath(
//		"./com/zb/jnlxc/JBPM/schemeFlow.jpdl.xml").deploy();
//		repositoryService.createDeployment().addResourceFromClasspath(
//		"./com/zb/jnlxc/JBPM/EditMouldFlow.jpdl.xml").deploy();
	}
	
}
