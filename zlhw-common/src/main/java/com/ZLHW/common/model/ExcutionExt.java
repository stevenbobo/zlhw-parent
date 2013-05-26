package com.ZLHW.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypeBase;
import com.ZLHW.base.Field.TypePrimary;
import com.ZLHW.base.HTable.HBaseTable;
import com.ZLHW.base.table.BaseTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_ExcutionExt")
@TableDeclare(comment = "流程附加字段", tableName = "Base_ExcutionExt")
public class ExcutionExt extends HBaseTable{

	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：ExcutionExt:流程附加字段
	//主键processInstanceId类型：String
	@Id
	@Column(length=60)
	private String processInstanceId; //流程编号
	private String str1; //附加字段1
	private String str2; //附加字段2
	private String str3; //附加字段3
	/**
	* 获取流程编号
	*/
	public String getProcessInstanceId(){
	    return this.processInstanceId;
	}
	/**
	* 设置流程编号
	* @param processInstanceId
	*/
	public void setProcessInstanceId(String processInstanceId){
	    this.processInstanceId = processInstanceId;
	}
	/**
	* 获取附加字段1
	*/
	public String getStr1(){
	    return this.str1;
	}
	/**
	* 设置附加字段1
	* @param str1
	*/
	public void setStr1(String str1){
	    this.str1 = str1;
	}
	/**
	* 获取附加字段2
	*/
	public String getStr2(){
	    return this.str2;
	}
	/**
	* 设置附加字段2
	* @param str2
	*/
	public void setStr2(String str2){
	    this.str2 = str2;
	}
	/**
	* 获取附加字段3
	*/
	public String getStr3(){
	    return this.str3;
	}
	/**
	* 设置附加字段3
	* @param str3
	*/
	public void setStr3(String str3){
	    this.str3 = str3;
	}

	@Override
	public Serializable primary() {
		return processInstanceId;
	}

	// 获取主键名称
	public  String primaryColumn(){
		return "processInstanceId";
	}
}
