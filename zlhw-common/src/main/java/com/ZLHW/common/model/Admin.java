package com.ZLHW.common.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;


import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_Admin")
@TableDeclare(comment = "用户表", tableName = "Base_Admin")
public class Admin extends HIntTable {
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：JNLXC_Admin:用户表
	//主键dbid类型：Integer
	@Column(length=60)
	private String account; //账号
	@Column(length=60)
	private String name; //名称
	@Column(length=60)
	private String password; //密码
	private Integer nextClientNum;//下一个客户号
	@ManyToOne
	private Organ_Job organ_Job; //机构-岗位
	@Column(length=1)
	private Integer state;//0:离职1:正常上班
	@Temporal(TemporalType.TIME)
	private Date addDate; //添加日期
	@Temporal(TemporalType.TIME)
	private Date lastlogin; //上次登录时间
	@Column(length=100)
	private String email; //电子邮件
	@Transient
	private List children; //前台需要级联显示的内容
	@Version
	private Integer version; //乐观锁:版本号
	/**
	* 获取乐观锁:版本号
	*/
	public Integer getVersion(){
	    return this.version;
	}
	/**
	* 设置乐观锁:版本号
	* @param version
	*/
	public void setVersion(Integer version){
	    this.version = version;
	}
	
	public Integer getNextClientNum(){
		return this.nextClientNum;
	}
	public void setNextClientNum(Integer nextClientNum){
		this.nextClientNum=nextClientNum;
	}
	public String getAccount(){
	    return this.account;
	}
	public void setAccount(String account){
	    this.account = account;
	}
	public String getName(){
	    return this.name;
	}
	public void setName(String name){
	    this.name = name;
	}
	public String getPassword(){
	    return this.password;
	}
	public void setPassword(String password){
	    this.password = password;
	}
	/**
	* 获取机构-岗位
	*/
	public Organ_Job getOrgan_Job(){
	    return this.organ_Job;
	}
	/**
	* 设置机构-岗位
	* @param organ_Job
	*/
	public void setOrgan_Job(Organ_Job organ_Job){
	    this.organ_Job = organ_Job;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getAddDate(){
	    return this.addDate;
	}
	public void setAddDate(Date addDate){
	    this.addDate = addDate;
	}
	public Date getLastlogin(){
	    return this.lastlogin;
	}
	public void setLastlogin(Date lastlogin){
	    this.lastlogin = lastlogin;
	}
	public String getEmail(){
	    return this.email;
	}
	public void setEmail(String email){
	    this.email = email;
	}
	public String getBusinessEmail() {
		return this.email;
	}
	public String getFamilyName() {
		return null;
	}
	public String getGivenName() {
		return null;
	}
	public String getId() {
		return this.getAccount();
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}

}
