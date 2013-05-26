package com.ZLHW.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypeBase;
import com.ZLHW.base.HTable.HCodeTable;
import com.ZLHW.base.table.CodeTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_DataDictCatalog")
@TableDeclare(comment = "数据字典目录", tableName = "Base_DataDictCatalog")
public class DataDictCatalog extends HCodeTable{

	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：JNLXC_DataDictionaryCatalog:数据字典目录
	//主键code类型：String
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
