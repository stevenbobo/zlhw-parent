package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;
import com.google.gson.GsonBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jbpm.api.identity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="JNLXC_Admin")
@TableDeclare(comment = "用户表", tableName = "Admin")
public class Admin extends HIntTable implements User{
    @Column(length=40)
    private String account; //账号
    @Column(length=50)
    private String name; //姓名
    @Column(length=40)
    private String password; //密码
    private Integer nextClientNum=1;//下一个客户号

    private String userGroups; //用户组
    private Byte state;//1:正常上班2:离职
    @Temporal(TemporalType.TIMESTAMP)
    private Date addDate; //添加日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlogin; //上次登录时间
    @ManyToOne
    private Depart depart; //部门表
    @Column(length=60)
    private String email; //电子邮件

    private byte lever;//等级

    public byte getLever() {
        return lever;
    }

    public void setLever(byte lever) {
        this.lever = lever;
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
	
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
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
	public Depart getDepart(){
	    return this.depart;
	}
	public void setDepart(Depart depart){
	    this.depart = depart;
	}
	public String getEmail(){
	    return this.email;
	}
	public void setEmail(String email){
	    this.email = email;
	}

    @JsonIgnore
	public String getBusinessEmail() {
		// TODO Auto-generated method stub
		return null;
	}

    @JsonIgnore
	public String getFamilyName() {
		return null;
	}

    @JsonIgnore
	public String getGivenName() {
		return null;
	}

    @JsonIgnore
	public String getId() {
		return this.getAccount();
	}

    public String getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(String userGroups) {
        this.userGroups = userGroups;
    }

}
