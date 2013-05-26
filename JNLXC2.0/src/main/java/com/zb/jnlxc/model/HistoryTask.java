package com.zb.jnlxc.model;

import java.util.Date;

import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;

public class HistoryTask {
	public HistoryTask(HistoryTaskInstanceImpl bean){
		taskid=bean.getHistoryTask().getId();
		activityName=bean.getActivityName();
		startTime=bean.getStartTime();
		endTime=bean.getEndTime();
		excutionId=bean.getHistoryProcessInstance().getProcessInstanceId();
		assiginee=bean.getHistoryTask().getAssignee();
		state=bean.getHistoryTask().getState();
	}
	private String taskid;
	//节点名
	private String activityName;
	//流程编号
	private String excutionId;
	//节点状态 completed或null
	private String state;
	//创建节点时间
	private Date startTime;
	//节点完成时间
	private Date endTime;
	//流入箭头名
	private String transition;
	//任务接受人
	private String assiginee;
	public String getTaskId() {
		return taskid;
	}
	public String getActivityName() {
		return activityName;
	}
	public String getExcutionId() {
		return excutionId;
	}
	public String getState() {
		return state;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getTransition() {
		return transition;
	}
	public String getAssiginee() {
		return assiginee;
	}

	
}
