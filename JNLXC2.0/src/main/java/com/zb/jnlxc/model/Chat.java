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
@Table(name="JNLXC_Chat")
@TableDeclare(comment = "聊天消息", tableName = "Chat")
public class Chat extends HIntTable{
	@ManyToOne
	private Admin sender; //发消息
	@ManyToOne
	private Admin received; //收消息人
	@Temporal(TemporalType.TIMESTAMP)
	private Date addTime; //添加时间
	@Column(length=1000)
	private String content; //聊天内容
	@Version  
    private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	* 获取发消息
	*/
	public Admin getSender(){
	    return this.sender;
	}
	/**
	* 设置发消息
	* @param sender
	*/
	public void setSender(Admin sender){
	    this.sender = sender;
	}
	/**
	* 获取收消息人
	*/
	public Admin getReceived(){
	    return this.received;
	}
	/**
	* 设置收消息人
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
	* 获取聊天内容
	*/
	public String getContent(){
	    return this.content;
	}
	/**
	* 设置聊天内容
	* @param content
	*/
	public void setContent(String content){
	    this.content = content;
	}

}
