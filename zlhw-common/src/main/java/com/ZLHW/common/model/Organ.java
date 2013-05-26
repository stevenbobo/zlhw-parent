package com.ZLHW.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypeBase;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_Organ")
@TableDeclare(comment = "用户表", tableName = "Base_Organ")
public class Organ extends HIntTable{

	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：Base_Admin:用户表
	//主键dbId类型：Integer
	@Column(length=20)
	private String code; //机构代号000100010001的形式
	@Column(length=40)
	private String name; //机构名
	private Integer lever; //机构层级,1级表示顶级机构
	private Integer parent; //机构层级
	/**
	* 获取机构代号
	*/
	public String getCode(){
	    return this.code;
	}
	/**
	* 设置机构代号
	* @param code
	*/
	public void setCode(String code){
	    this.code = code;
	}
	/**
	* 获取机构名
	*/
	public String getName(){
	    return this.name;
	}
	/**
	* 设置机构名
	* @param name
	*/
	public void setName(String name){
	    this.name = name;
	}
	/**
	* 获取机构层级,1级表示顶级机构
	*/
	public Integer getLever(){
	    return this.lever;
	}
	/**
	* 设置机构层级,1级表示顶级机构
	* @param lever
	*/
	public void setLever(Integer lever){
	    this.lever = lever;
	}
	/**
	* 获取机构层级
	*/
	public Integer getParent(){
	    return this.parent;
	}
	/**
	* 设置机构层级
	* @param parent
	*/
	public void setParent(Integer parent){
	    this.parent = parent;
	}
	public String getId() {
		return this.getCode();
	}
	public String getType() {
		return null;
	}

}
