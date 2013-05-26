package com.ZLHW.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypeBase;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_AuthorDetail")
@TableDeclare(comment = "权限明细表", tableName = "Base_AuthorDetail")
public class AuthorDetail extends HIntTable{

	
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：JNLXC_AuthorDetail:权限明细表
	//主键id类型：Integer
	@Column(length=60)
	private String name; //名称
	@Column(length=100)
	private String path; //returnView的包路径
	@Column(length=60)
	private String beanName; //bean名称
	@Column(length=100)
	private String beanFunction; //bean函数
	@Column(length=1)
	private Integer status; //当前状态:0菜单可见,1菜单不可见
	@ManyToOne
	private Author author; //权限表
	@Transient
	private Boolean selected;//
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public String getName(){
	    return this.name;
	}
	public void setName(String name){
	    this.name = name;
	}
	public String getPath(){
	    return this.path;
	}
	public void setPath(String path){
	    this.path = path;
	}
	public String getBeanName(){
	    return this.beanName;
	}
	public void setBeanName(String beanName){
	    this.beanName = beanName;
	}
	public String getBeanFunction(){
	    return this.beanFunction;
	}
	public void setBeanFunction(String beanFunction){
	    this.beanFunction = beanFunction;
	}
	public Integer getStatus(){
	    return this.status;
	}
	public void setStatus(Integer status){
	    this.status = status;
	}
	public Author getAuthor(){
	    return this.author;
	}
	public void setAuthor(Author author){
	    this.author = author;
	}


}
