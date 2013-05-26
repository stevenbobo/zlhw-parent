package com.ZLHW.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_Job")
@TableDeclare(comment = "岗位", tableName = "Base_Job")
public class Job extends HIntTable{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：JNLXC_Base_Job:岗位
	//主键dbId类型：Integer
	@Column(length=60)
	private String name; //岗位名称
	/**
	* 获取岗位名称
	*/
	public String getName(){
	    return this.name;
	}
	/**
	* 设置岗位名称
	* @param name
	*/
	public void setName(String name){
	    this.name = name;
	}

}
