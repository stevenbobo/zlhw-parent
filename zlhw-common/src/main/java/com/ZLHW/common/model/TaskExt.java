package com.ZLHW.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ZLHW.base.HTable.HBaseTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_TaskExt")
@TableDeclare(comment = "任务附加字段", tableName = "Base_TaskExt")
public class TaskExt extends HBaseTable{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：Base_TaskExt:任务附加字段
	//主键taskId类型：String
	@Id
	@Column(length=32)
	private String taskId; //任务编号
	@Column(length=60)
	private String processInstanceId; //所属流程编号
	private String Comments; //意见
	/**
	* 获取任务编号
	*/
	public String getTaskId(){
	    return this.taskId;
	}
	/**
	* 设置任务编号
	* @param taskId
	*/
	public void setTaskId(String taskId){
	    this.taskId = taskId;
	}
	/**
	* 获取所属流程编号
	*/
	public String getProcessInstanceId(){
	    return this.processInstanceId;
	}
	/**
	* 设置所属流程编号
	* @param processInstanceId
	*/
	public void setProcessInstanceId(String processInstanceId){
	    this.processInstanceId = processInstanceId;
	}
	/**
	* 获取意见
	*/
	public String getComments(){
	    return this.Comments;
	}
	/**
	* 设置意见
	* @param Comments
	*/
	public void setComments(String Comments){
	    this.Comments = Comments;
	}

	@Override
	public Serializable primary() {
		return taskId;
	}

	// 获取主键名称
	public  String primaryColumn(){
		return "taskId";
	}

}
