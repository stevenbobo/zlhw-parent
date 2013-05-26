package com.zb.jnlxc.model;

import java.util.Date;

import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
/**
 * 转化下JBPM自带的Task
 * @author Administrator
 *
 */
public class MyTask {
	private String id; //任务id
	private String name; //任务名
	private Date createTime; //发起流程时间
	private Date duedate;//截止时间
	private String executionId;//当前流程id
	private String formResourceName;//对应前台表单路径
	private String assingnee;//任务接受人
	public MyTask(TaskImpl task){
		this.id=task.getId();
		this.name=task.getName();
		this.createTime=task.getCreateTime();
		this.duedate=task.getDuedate();
		this.executionId=task.getProcessInstance().getId();
		this.formResourceName=task.getFormResourceName();
		this.assingnee=task.getAssignee();
	}
	
	
	
	public String getAssingnee() {
		return assingnee;
	}

	public void setAssingnee(String assingnee) {
		this.assingnee = assingnee;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getFormResourceName() {
		return formResourceName;
	}
	public void setFormResourceName(String formResourceName) {
		this.formResourceName = formResourceName;
	}
	
	
}
