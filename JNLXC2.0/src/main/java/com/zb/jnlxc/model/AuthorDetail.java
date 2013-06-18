package com.zb.jnlxc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_AuthorDetail")
@TableDeclare(comment = "权限明细表", tableName = "AuthorDetail")
public class AuthorDetail extends HIntTable{
	@Column(length=40)
	private String name; //名称
	@Column(length=100)
	private String path; //returnView的包路径
	@Column(length=40)
	private String beanName; //bean名称
	@Column(length=40)
	private String beanFunction; //bean函数
	private Byte status; //当前状态:0菜单可见,1菜单不可见
    @Column(length=10)
    private Integer parentId;    //父菜单编号

	private Boolean selected;//

    private Byte authorLever;//操作等级,1为最高级别
	@Version  
    private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
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
	public Byte getStatus(){
	    return this.status;
	}
	public void setStatus(Byte status){
	    this.status = status;
	}

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Byte getAuthorLever() {
        return authorLever;
    }

    public void setAuthorLever(Byte authorLever) {
        this.authorLever = authorLever;
    }
}
