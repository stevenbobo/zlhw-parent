package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HCodeTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Base_DataDictCatalog")
@TableDeclare(comment = "数据字典目录", tableName = "Base_DataDictCatalog")
public class DataDictCatalog extends HCodeTable{

	@Column(length=60)
	private String name; //名称
	private String description; //描述
	/**
	* 获取名称
	*/
	public String getName(){
	    return this.name;
	}
	/**
	* 设置名称
	* @param name
	*/
	public void setName(String name){
	    this.name = name;
	}
	/**
	* 获取描述
	*/
	public String getDescription(){
	    return this.description;
	}
	/**
	* 设置描述
	* @param description
	*/
	public void setDescription(String description){
	    this.description = description;
	}
	

}
