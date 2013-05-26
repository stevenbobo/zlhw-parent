package com.ZLHW.common.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_Author")
@TableDeclare(comment = "权限表", tableName = "Base_Author")
public class Author extends HIntTable{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：Author:权限表
	//主键id类型：Integer
	@Column(length=60)
	private String name; //名称
	@Transient
	private List<AuthorDetail> children; //前端需要赋予显示的子权限
	@Transient
	private boolean selected;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getName(){
	    return this.name;
	}
	public void setName(String name){
	    this.name = name;
	}
	public List<AuthorDetail> getChildren() {
		return children;
	}
	public void setChildren(List<AuthorDetail> children) {
		this.children = children;
	}

	
	

}
