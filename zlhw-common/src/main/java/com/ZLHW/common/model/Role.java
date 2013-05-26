package com.ZLHW.common.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_Role")
@TableDeclare(comment = "角色", tableName = "Base_Role")
public class Role extends HIntTable {
	
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：JNLXC_Role:角色
	//主键dbid类型：Integer
	@Column(length=50)
	private String name; //名称
	@Transient
	private List children;

	public List getChildren() {
		return children;
	}
	
	public void setChildren(List children) {
		this.children = children;
	}
	
	public String getName(){
	    return this.name;
	}
	public void setName(String name){
	    this.name = name;
	}
	public String getId() {
		return name;
	}
	public String getType() {
		return null;
	}
	


}
