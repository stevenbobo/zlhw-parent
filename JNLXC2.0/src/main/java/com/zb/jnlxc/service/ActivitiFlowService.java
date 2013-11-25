package com.zb.jnlxc.service;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.FlowStateImage;
import com.zb.jnlxc.model.HistoryTask;
import com.zb.jnlxc.model.MyTask;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* Created with IntelliJ IDEA.
* User: zhengbo.zb
* Date: 13-11-18
* Time: 下午11:50
* To change this template use File | Settings | File Templates.
*/
public class ActivitiFlowService  {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private FormService formService;
    @Resource
    private HistoryService historyService;
    @Resource
    private ManagementService managementService;

    
    public Page loadTask(Admin admin, Page page) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,String> map = new HashMap<String, String>();
        if(StringUtils.isNotEmpty(page.getParameter("data")))
            map =  new Gson().fromJson(page.getParameter("data"),new TypeToken<Map<String,String>>(){}.getType());
        TaskQuery taskQuery = taskService.createTaskQuery();

        if(com.zb.util.StringUtils.isNotEmpty(map.get("startDate"))){
            Date startDate=sdf.parse(map.get("startDate"));
            taskQuery.dueAfter(startDate);
        }
        if(com.zb.util.StringUtils.isNotEmpty(map.get("endDate"))){
            Date endDate=sdf.parse(map.get("endDate"));
            taskQuery.dueBefore(endDate);
        }

        if(StringUtils.isNotEmpty(map.get("excutionkey"))){
            taskQuery.executionId(map.get("excutionkey"));
        }

        if(com.zb.util.StringUtils.isNotEmpty(map.get("workFlowType"))){
            taskQuery.processDefinitionKey(map.get("workFlowType"));
        }
        List<Task> tasks = taskQuery.taskCandidateUser(admin.getAccount()).list();
        page.setResultData(tasks);
        return page;    //To change body of overridden methods use File | Settings | File Templates.
    }

    
    public MyTask takeTask(String taskId, Admin user) {
        taskService.claim(taskId,user.getAccount());
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String formKey = formService.getStartFormKey(taskId);
        return new MyTask(task,formKey);
    }

    
    public MyTask cancleTakeTask(String taskId) throws BaseErrorModel {
        try {
            taskService.unclaim(taskId);
        }catch (Exception e){
            throw new BaseErrorModel("私有任务，无法取消已接受任务", "");
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String formKey = formService.getStartFormKey(taskId);
        return new MyTask(task,formKey);
    }

    
//    public List findTaskOperaters(String taskId) {
//        return super.findTaskOperaters(taskId);    //To change body of overridden methods use File | Settings | File Templates.
//    }

    
    public List<HistoricTaskInstance> getHistoryByExcutionId(String excutionId) {
        return historyService.createHistoricTaskInstanceQuery().executionId(excutionId).list();
    }

    
    public void addOutTransition(String taskIds, String destName, Map variables) {
//        super.addOutTransition(taskIds, destName, variables);    //To change body of overridden methods use File | Settings | File Templates.
    }

    
//    public FlowStateImage getFlowStateImage(String taskId) {
//        return super.getFlowStateImage(taskId);    //To change body of overridden methods use File | Settings | File Templates.
//    }

    
//    public FlowStateImage getFlowStateImageByExcutionId(String excutionId) {
//        return super.getFlowStateImageByExcutionId(excutionId);    //To change body of overridden methods use File | Settings | File Templates.
//    }

    
    public void completeTask(String taskId, Admin user) {
        taskService.complete(taskId);
    }

    
    public void completeTask(String taskId, Map map, Admin user) throws BaseErrorModel {
        taskService.complete(taskId, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    
//    public void completeTask(String taskId, String nextName, Admin user) {
//        super.completeTask(taskId, nextName, user);    //To change body of overridden methods use File | Settings | File Templates.
//    }

    
//    public void completeTask(String taskId, String nextName, Map map, Admin user) {
//        super.completeTask(taskId, nextName, map, user);    //To change body of overridden methods use File | Settings | File Templates.
//    }

    
    public String deployFlow(String path) {
        return repositoryService.createDeployment().addClasspathResource(path).deploy().getId();
    }

    
    public void deleteDeploymentCascade(String id) {
        repositoryService.deleteDeployment(id,true);
    }

    public void startProcessInstanceByKey(String flowName, String id) {
        runtimeService.startProcessInstanceByKey(flowName,id);
    }

    
    public void deleteProcessInstanceCascade(String id) throws BaseErrorModel {
        List<Execution> executions = runtimeService.createExecutionQuery().executionId(id).list();
        if(executions.size()>0){
           runtimeService.deleteProcessInstance(executions.get(0).getProcessInstanceId(),"正常删除");
        }
    }

    
    public void startProcessInstanceByKey(String FlowName, String id, Map map) {
        runtimeService.startProcessInstanceByKey(FlowName, id, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    
    public ProcessInstance findProcessInstanceByKey(String flowName, String id) {
        return getProcessInstanceById(flowName+"."+id);
    }

    public Task getTaskById(String taskId){
        List<Task> tasks = taskService.createTaskQuery().taskId(taskId).list();
        if(tasks.size()>0)
            return tasks.get(0);
        else
            throw new BaseErrorModel("无此任务", "");
    }

    public ProcessInstance getProcessInstanceById(String executionId){
        List<ProcessInstance> processInstances =  runtimeService.createProcessInstanceQuery().processInstanceId(executionId).list();
        if(processInstances.size()>0)
            return processInstances.get(0);
        else
            throw new BaseErrorModel("无此流程实例", "");
    }

    
    public Object getContentMap(String taskId, String key) {
        Task task = getTaskById(taskId);
        return task.getProcessVariables().get(key);    //To change body of overridden methods use File | Settings | File Templates.
    }

    
    public Object getContentMapByExecution(String executionId, String key) {
        ProcessInstance processInstance = getProcessInstanceById(executionId);
        return processInstance.getProcessVariables().get(key);
    }
}
