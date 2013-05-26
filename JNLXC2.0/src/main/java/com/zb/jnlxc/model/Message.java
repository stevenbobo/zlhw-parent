package com.zb.jnlxc.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="JNLXC_Message")
@TableDeclare(comment = "短消息", tableName = "Message")
public class Message extends HIntTable{
	@ManyToOne
	private Admin sender; //发信息人
	@ManyToOne
	private Depart received_Depart; //收信息部门
	@ManyToOne
	private Admin received; //收信息人
	@Temporal(TemporalType.TIMESTAMP)
	private Date addTime; //添加时间
	@Column(length=50)
	private String title; //短信标题
	@Column(length=1000)
	private String content; //短信内容
	private Byte status; //当前状态
	private Integer replyto; //如果是答复短信则不为空
	@Version  
    private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	* 获取发信息人
	*/
	public Admin getSender(){
	    return this.sender;
	}
	/**
	* 设置发信息人
	* @param sender
	*/
	public void setSender(Admin sender){
	    this.sender = sender;
	}
	/**
	* 获取收信息部门
	*/
	public Depart getReceived_Depart(){
	    return this.received_Depart;
	}
	/**
	* 设置收信息部门
	* @param received_Depart
	*/
	public void setReceived_Depart(Depart received_Depart){
	    this.received_Depart = received_Depart;
	}
	/**
	* 获取收信息人
	*/
	public Admin getReceived(){
	    return this.received;
	}
	/**
	* 设置收信息人
	* @param received
	*/
	public void setReceived(Admin received){
	    this.received = received;
	}
	/**
	* 获取添加时间
	*/
	public Date getAddTime(){
	    return this.addTime;
	}
	/**
	* 设置添加时间
	* @param addTime
	*/
	public void setAddTime(Date addTime){
	    this.addTime = addTime;
	}
	/**
	* 获取短信标题
	*/
	public String getTitle(){
	    return this.title;
	}
	/**
	* 设置短信标题
	* @param title
	*/
	public void setTitle(String title){
	    this.title = title;
	}
	/**
	* 获取短信内容
	*/
	public String getContent(){
	    return this.content;
	}
	/**
	* 设置短信内容
	* @param content
	*/
	public void setContent(String content){
	    this.content = content;
	}
	/**
	* 获取当前状态
	*/
	public Byte getStatus(){
	    return this.status;
	}
	/**
	* 设置当前状态
	* @param status
	*/
	public void setStatus(Byte status){
	    this.status = status;
	}
	/**
	* 获取如果是答复短信则不为空
	*/
	public Integer getReplyto(){
	    return this.replyto;
	}
	/**
	* 设置如果是答复短信则不为空
	* @param replyto
	*/
	public void setReplyto(Integer replyto){
	    this.replyto = replyto;
	}
	
	
}
