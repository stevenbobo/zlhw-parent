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
@Table(name="Base_Organ_Job")
@TableDeclare(comment = "机构-岗位", tableName = "Base_Organ_Job")
public class Organ_Job extends HIntTable{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：Base_Organ_Job:机构-岗位
	//主键dbId类型：Integer
	@ManyToOne
	private Organ organ; //用户表
	@ManyToOne
	private Job job; //岗位
	/**
	* 获取用户表
	*/
	public Organ getOrgan(){
	    return this.organ;
	}
	/**
	* 设置用户表
	* @param organ
	*/
	public void setOrgan(Organ organ){
	    this.organ = organ;
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
