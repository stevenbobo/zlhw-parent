package com.zb.jnlxc.service;

import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.Deployment;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class FlowServiceTest extends AbstractJUnit4SpringContextTests  {
	@Resource
	FlowService flowService;


	public void testDeployFlowAll() {
		String path1 = "./jbpm/mouldFlow.jpdl.xml";
		String path2 = "./jbpm/orderFlow.jpdl.xml";
		String path3 = "./jbpm/paidan.jpdl.xml";
		String path4 = "./jbpm/productRecordFlow.jpdl.xml";
		String path5 = "./jbpm/schemeFlow.jpdl.xml";
		String result1 = flowService.deployFlow(path1);
		String result2 = flowService.deployFlow(path2);
		String result3 = flowService.deployFlow(path3);
		String result4 = flowService.deployFlow(path4);
		String result5 = flowService.deployFlow(path5);
	}
    @Test
    public void testDeployMouldFlow() {
        String path1 = "jbpm/mouldFlow.jpdl.xml";
        String path2 = "./jbpm/mouldProcess.jpdl.xml";
        String result1 = flowService.deployFlow(path1);
        String result2 = flowService.deployFlow(path2);
    }

	public void testDeleteFlow(){
		List<Deployment> list = flowService.getRepositoryService().createDeploymentQuery().list();
		for(Deployment deployment:list){
			flowService.deleteDeploymentCascade(deployment.getId());
		}
        testDeployFlowAll();
	}
    @Test
	public void testDeleteInstance(){
		List<ProcessInstance> list1 = flowService.getExecutionService().createProcessInstanceQuery().list();
		for(ProcessInstance processInstance:list1){
            logger.info(processInstance.getId());
//			flowService.getExecutionService().deleteProcessInstanceCascade(processInstance.getId());
		}
	}
   @Test
    public void testHistoryService(){
        TaskImpl task = (TaskImpl) flowService.getTaskService().getTask(""+2640015);
       HistoryTaskInstanceImpl historyTaskInstance = (HistoryTaskInstanceImpl) flowService.getHistoryService().createHistoryActivityInstanceQuery().executionId(task.getExecutionId()).orderDesc("dbid").list().get(1);
        logger.info(historyTaskInstance.getActivityName());
    }

}
