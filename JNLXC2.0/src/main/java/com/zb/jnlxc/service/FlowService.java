package com.zb.jnlxc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.ZLHW.base.dao.QueryCondition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.FlowStateImage;
import com.zb.jnlxc.model.HistoryTask;
import com.zb.jnlxc.model.ImgLocate;
import com.zb.jnlxc.model.MyTask;

@Service
@Transactional
public class FlowService extends BaseService{
	@Resource
	private ProcessEngine processEngine;
	
	/**
	 * 工作流引擎，用于获取服务(通过spring注入获取)
	 * @return
	 */
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	/**
	 * 包含了用来管理发布流程的所有方法
	 * @return
	 */
	public RepositoryService getRepositoryService() {
		return processEngine.getRepositoryService();
	}

	/**
	 * 用来执行流程
	 * @return
	 */
	public ExecutionService getExecutionService() {
		return processEngine.getExecutionService();
	}

	/**
	 * 提供对任务列表的访问途径和任务处理
	 * @return
	 */
	public TaskService getTaskService() {
		return processEngine.getTaskService();
	}

	/**
	 * 历史记录服务
	 * @return
	 */
	public HistoryService getHistoryService() {
		return processEngine.getHistoryService();
	}

	/**
	 * JBPM自带的用户-用户组管理服务
	 * @return
	 */
	public IdentityService getIdentityService() {
		return processEngine.getIdentityService();
	}

	/**
	 * 管理服务通常用来管理job
	 * @return
	 */
	public ManagementService getManagementService() {
		return processEngine.getManagementService();
	}


    /**
     * 查找当前用户的所有任务
     * @return
     */
	public Page loadTask(Admin admin,Page page) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<QueryCondition> queryConditions=new ArrayList();
        Map<String,String> map = new HashMap<String, String>();
        if(StringUtils.isNotEmpty(page.getParameter("data")))
            map =  new Gson().fromJson(page.getParameter("data"),new TypeToken<Map<String,String>>(){}.getType());

	    StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(TaskImpl.class.getName()).append(" t where 1=1 ");

        String account=admin.getAccount();
        hql.append("and (t.assignee = :account " +
                "or t in (select p.task from " + ParticipationImpl.class.getName() + " p where p.type='candidate' and p.userId= :account)) ");
        queryConditions.add(new QueryCondition("account", account));

        if(com.zb.util.StringUtils.isNotEmpty(map.get("startDate"))){
            hql.append("and t.createTime >=:startDate ");
            Date startDate=sdf.parse(map.get("startDate"));
            queryConditions.add(new QueryCondition("startDate", startDate));
        }
        if(com.zb.util.StringUtils.isNotEmpty(map.get("endDate"))){
            hql.append("and t.createTime <:endDate ");
            Date endDate=sdf.parse(map.get("endDate"));
            queryConditions.add(new QueryCondition("endDate", endDate));
        }

        if(StringUtils.isNotEmpty(map.get("excutionkey"))){
            hql.append(" and t.processInstance.key  =:excutionkey ");
            queryConditions.add(new QueryCondition("excutionkey", map.get("excutionkey")));
        }

        if(com.zb.util.StringUtils.isNotEmpty(map.get("workFlowType"))){
            hql.append(" and t.executionId like :workFlowType ");
            queryConditions.add(new QueryCondition("workFlowType", map.get("workFlowType")+"%"));
        }

        hql.append(" order by t.createTime desc");
        this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        List<TaskImpl> list = page.getResultData();
        List<MyTask> newList = new ArrayList<MyTask>();
        for(TaskImpl task:list){
            newList.add(new MyTask(task));
        }
        page.setResultData(newList);
        return page;
	}
	

	/**
	 * 接受一个任务（针对于任务分配给用户组的情况）
	 * @param taskId 任务id
	 */
	public MyTask takeTask(String taskId,Admin user){
		this.getTaskService().takeTask(taskId,user.getAccount());
		return new MyTask((TaskImpl) this.getTaskService().getTask(taskId));
	}
	
	public MyTask cancleTakeTask(String taskId) throws BaseErrorModel{
		List<ParticipationImpl> list=this.getDao().findByHQL(" from org.jbpm.pvm.internal.task.ParticipationImpl t where t.task.dbid = ?",Long.parseLong(taskId));
		if(list.size()==0)
			throw new BaseErrorModel("私有任务，无法取消已接受任务", "");
		this.getTaskService().assignTask(taskId, null);
		return new MyTask((TaskImpl) this.getTaskService().getTask(taskId));
	}
	/**
	 * 查找任务列表
	 * @param taskId
	 * @return
	 */
	public List<ParticipationImpl> findTaskOperaters(String taskId){
		List<ParticipationImpl> list=this.getDao().findByHQL(" from org.jbpm.pvm.internal.task.ParticipationImpl t where t.task.dbid = ?",Long.parseLong(taskId));
		return list;
	}
	
	
	/**
	 * 获取用户指定类型的任务记录
	 * @return
	 */
	public List<HistoryTask> getHistoryByExcutionId(String excutionId){
		String HQL = "from "+HistoryActivityInstanceImpl.class.getName()+" t where t.historyProcessInstance.processInstanceId = '"+excutionId+"'";
		List<HistoryTaskInstanceImpl> list=this.getDao().getSession().createQuery(HQL).list();
		List<HistoryTask> l=new ArrayList();
		for(HistoryTaskInstanceImpl hai:list){
			l.add(new HistoryTask(hai));
		}
		return l;
	}
	
	   /** 
	   * 动态创建连接当前任务节点至名称为destName的节点的Transition 
	   * @param destName  目标节点名称
	   * @param variables 流程参数
	   */
	  public void addOutTransition(String taskIds,String destName,Map variables){
		  TaskImpl sourceTask=(TaskImpl) this.getTaskService().getTask(taskIds);
	   EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
	   EnvironmentImpl env=null;
	   try {
		   ProcessDefinitionImpl pd=(ProcessDefinitionImpl) this.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(sourceTask.getProcessInstance().getProcessDefinitionId()).uniqueResult();
	       env = environmentFactory.openEnvironment();
	       //取得当前流程的活动定定义
	       ActivityImpl sourceActivity = pd.findActivity(sourceTask.getActivityName());
	       //取得目标的活动定义
	       ActivityImpl destActivity=pd.findActivity(destName);
	       //为两个节点创建连接
	       TransitionImpl transition = sourceActivity.createOutgoingTransition();
	       transition.setName("to" + destName);
	       transition.setDestination(destActivity);
	       sourceActivity.addOutgoingTransition(transition);
	       System.out.println("sourceActivity.getName() = "+sourceActivity.getName());
	       System.out.println("destActivity.getName() = "+destActivity.getName());
	       this.getTaskService().completeTask(sourceTask.getId(),transition.getName(),variables);
	   }catch(Exception ex){
	       ex.getMessage();
	   }finally{  
	       env.close();
	   }
	}
	
	/**
	 * 根据任务id获取流程相关信息
	 * @param taskId
	 * @return
	 */
	public FlowStateImage getFlowStateImage(String taskId){
		Task task=this.getTaskService().getTask(taskId);
		return getFlowStateImageByExcutionId(task.getExecutionId());
	}
	/**
	 * 根据流程实例id获取流程相关信息
	 * @param excutionId
	 * @return
	 */
	public FlowStateImage getFlowStateImageByExcutionId(String excutionId){
		String flowName=excutionId.split("\\.")[0];
		FlowStateImage flowStateImage=new FlowStateImage();
		flowStateImage.setUrl("FlowImg/"+flowName+".png");
		Execution execution=this.getExecutionService().findExecutionById(excutionId);
		if(execution!=null){
		ProcessInstance processInstance=(ProcessInstance) execution.getProcessInstance();
		Set<String> activityNames = processInstance.findActiveActivityNames();
		Set<ImgLocate> imageLocates=new HashSet();
		for(String activityName:activityNames){
		ActivityCoordinates ac = this.getRepositoryService().getActivityCoordinates(processInstance.getProcessDefinitionId(),activityName);
		ImgLocate imageLocate=new ImgLocate(ac.getX(),ac.getY(),ac.getWidth(),ac.getHeight());
		imageLocates.add(imageLocate);
		}
		flowStateImage.setImageLocates(imageLocates);
		return flowStateImage;
		}else{
			throw  new BaseErrorModel("无此流程","");
		}
	}

	/**
	 * 相对于流程图中无选择的情况
	 * @param taskId 任务号
	 */
	public void completeTask(String taskId,Admin user){
		if(this.getTaskService().getTask(taskId).getAssignee()==null)
		takeTask(taskId,user);
		this.getTaskService().completeTask(taskId);
	}
	/**
	 * 相对于流程图中无选择的情况,带返回参数
	 * @param taskId
	 * @param map 传递给流程的参数
	 * @throws BaseErrorModel 
	 */
	public void completeTask(String taskId,Map map,Admin user) throws BaseErrorModel{
		if(this.getTaskService().getTask(taskId).getAssignee()==null)
		takeTask(taskId,user);
		Set<String> set=this.getTaskService().getOutcomes(taskId);
		if(set.size()==1)
			this.getTaskService().completeTask(taskId,set.iterator().next(),map);
		else
			throw new BaseErrorModel("流程错误", "");
		
	}
	
	/**
	 * 有选择的情况下
	 * @param taskId 任务号
	 * @param nextName 箭头名
	 */
	public void completeTask(String taskId,String nextName,Admin user){
		if(this.getTaskService().getTask(taskId).getAssignee()==null)
		takeTask(taskId,user);
		this.getTaskService().completeTask(taskId,nextName);
	}
	
	
	/**
	 * 有选择的情况下,带返回参数
	 * @param taskId 任务号
	 * @param nextName 箭头名
	 * @param map 传递给流程的参数
	 */
	public void completeTask(String taskId,String nextName,Map map,Admin user){
		if(this.getTaskService().getTask(taskId).getAssignee()==null)
		takeTask(taskId,user);
		this.getTaskService().completeTask(taskId,nextName, map);
	}

	/**
	 * 发布一个新流程
	 * @param path 流程图jpdl位置（比如"./com/zb/JBPM/orderFlow.jpdl.xml"）
	 * @return
	 */
	public String deployFlow(String path){
		return this.getRepositoryService().createDeployment().addResourceFromClasspath(path).deploy();
	}
	/**
	 * 删除流程图版本
	 * @param id
	 */
	public void deleteDeploymentCascade(String id){
		this.getRepositoryService().deleteDeploymentCascade(id);
		
	}
	/**
	 * 凡是启动一个流程，必须有定义唯一的key，通常是相关流程涉及主表的主键id
	 * @param FlowName 发布的流程名
	 * @param id 流程涉及主表的主键id作为key
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String FlowName,String id){
		return this.getExecutionService().startProcessInstanceByKey(FlowName, id);
	}
	/**
	 * 删除一个正在执行的流程
	 * @param id 流程id
	 */
	public void deleteProcessInstanceCascade(String id) throws BaseErrorModel{
		Execution execution=this.getExecutionService().findExecutionById(id);
		if(execution!=null){
            ProcessInstance processInstance=(ProcessInstance) execution.getProcessInstance();
            this.getExecutionService().deleteProcessInstanceCascade(processInstance.getId());
		}
	}
	/**
	 * 凡是启动一个流程，必须有定义唯一的key，通常是相关流程涉及主表的主键id
	 * @param FlowName 发布的流程名
	 * @param id 流程涉及主表的主键id作为key
	 * @param map 开启流程所需的变量
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String FlowName,String id,Map map){
		return this.getExecutionService().startProcessInstanceByKey(FlowName, map,id);
	}
	
	/**
	 * 根据流程名和key获取流程实例
	 * @param FlowName 流程名
	 * @param id 主键key
	 * @return
	 */
	public ProcessInstance findProcessInstanceByKey(String FlowName,String id){
		return this.getExecutionService().findProcessInstanceById(FlowName+"."+id);
	}
	
	/**
	 * 获取流程实例的内容
	 * @param taskId 流程实例id
	 * @param key
	 * @return
	 */
	public Object getContentMap(String taskId,String key){
		String ExecutiongId=this.getTaskService().getTask(taskId).getExecutionId();
		return this.getExecutionService().getVariable(ExecutiongId, key);
	}
	/**
	 * 获取流程实例的内容
	 * @param executionId 流程实例id
	 * @param key 
	 * @return
	 */
	public Object getContentMapByExecution(String executionId,String key){
		String ExecutiongId=this.getExecutionService().findProcessInstanceById(executionId).getId();
		return this.getExecutionService().getVariable(ExecutiongId, key);
	}
	
}
