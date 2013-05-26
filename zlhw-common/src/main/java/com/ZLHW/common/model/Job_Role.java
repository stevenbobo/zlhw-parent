package com.ZLHW.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_Job_Role")
@TableDeclare(comment = "角色-岗位", tableName = "Base_Job_Role")
public class Job_Role extends HIntTable{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：Base_Job_Role:角色-岗位
	//主键dbId类型：Integer
	@ManyToOne
	private Role role; //用户组
	@ManyToOne
	private Job job; //岗位
	/**
	* 获取用户组
	*/
	public Role getRole(){
	    return this.role;
	}
	/**
	* 设置用户组
	* @param role
	*/
	public void setRole(Role role){
	    this.role = role;
	}
	/**
	* 获取岗位
	*/
	public Job getJob(){
	    return this.job;
	}
	/**
	* 设置岗位
	* @param job
	*/
	public void setJob(Job job){
	    this.job = job;
	}

}
