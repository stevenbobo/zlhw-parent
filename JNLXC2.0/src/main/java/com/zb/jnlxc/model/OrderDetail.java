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
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="JNLXC_OrderDetail")
@TableDeclare(comment = "订单明细，记录订购的产品信息", tableName = "OrderDetail")
@Optimistic
public class OrderDetail extends HIntTable{
	@ManyToOne
	private OrderForm orderForm; //订单表
	@Column(length=50)
	private String setSize; //规定尺寸
	private Integer orderQuantity; //订货数量
	private Integer orderWeight; //订货重量（千克）
	private Integer minWeight; //最小重量（千克）
	private Integer maxWeight; //最大重量（千克）
	private Integer compQuantity; //完成数量
	private Integer minQuantity; //最小数量
	private Integer maxQuantity; //最大数量
	private Integer compWeight; //完成重量（千克）
	private Byte compStatus; //完成状态0:未完成1：已完成
	@Temporal(TemporalType.TIMESTAMP)
	private Date preSendDate; //预计发货日期
	@Column(length=1024)
	private String remarks; //备注
	private Byte enable; //0正常， 1 已删除
//	@Version  
//    private Integer version;
	
//	public Integer getVersion() {
//		return version;
//	}
//	public void setVersion(Integer version) {
//		this.version = version;
//	}
	/**
	* 获取订单表
	*/
	public OrderForm getOrderForm(){
	    return this.orderForm;
	}
	/**
	* 设置订单表
	* @param orderForm
	*/
	public void setOrderForm(OrderForm orderForm){
	    this.orderForm = orderForm;
	}
	/**
	* 获取规定尺寸
	*/
	public String getSetSize(){
	    return this.setSize;
	}
	/**
	* 设置规定尺寸
	* @param setSize
	*/
	public void setSetSize(String setSize){
	    this.setSize = setSize;
	}
	/**
	* 获取订货数量
	*/
	public Integer getOrderQuantity(){
	    return this.orderQuantity;
	}
	/**
	* 设置订货数量
	* @param orderQuantity
	*/
	public void setOrderQuantity(Integer orderQuantity){
	    this.orderQuantity = orderQuantity;
	}
	/**
	* 获取订货重量（千克）
	*/
	public Integer getOrderWeight(){
	    return this.orderWeight;
	}
	/**
	* 设置订货重量（千克）
	* @param orderWeight
	*/
	public void setOrderWeight(Integer orderWeight){
	    this.orderWeight = orderWeight;
	}
	/**
	* 获取最小重量（千克）
	*/
	public Integer getMinWeight(){
	    return this.minWeight;
	}
	/**
	* 设置最小重量（千克）
	* @param minWeight
	*/
	public void setMinWeight(Integer minWeight){
	    this.minWeight = minWeight;
	}
	/**
	* 获取最大重量（千克）
	*/
	public Integer getMaxWeight(){
	    return this.maxWeight;
	}
	/**
	* 设置最大重量（千克）
	* @param maxWeight
	*/
	public void setMaxWeight(Integer maxWeight){
	    this.maxWeight = maxWeight;
	}
	/**
	* 获取完成数量
	*/
	public Integer getCompQuantity(){
	    return this.compQuantity;
	}
	/**
	* 设置完成数量
	* @param compQuantity
	*/
	public void setCompQuantity(Integer compQuantity){
	    this.compQuantity = compQuantity;
	}
	/**
	* 获取最小数量
	*/
	public Integer getMinQuantity(){
	    return this.minQuantity;
	}
	/**
	* 设置最小数量
	* @param minQuantity
	*/
	public void setMinQuantity(Integer minQuantity){
	    this.minQuantity = minQuantity;
	}
	/**
	* 获取最大数量
	*/
	public Integer getMaxQuantity(){
	    return this.maxQuantity;
	}
	/**
	* 设置最大数量
	* @param maxQuantity
	*/
	public void setMaxQuantity(Integer maxQuantity){
	    this.maxQuantity = maxQuantity;
	}
	/**
	* 获取完成重量（千克）
	*/
	public Integer getCompWeight(){
	    return this.compWeight;
	}
	/**
	* 设置完成重量（千克）
	* @param compWeight
	*/
	public void setCompWeight(Integer compWeight){
	    this.compWeight = compWeight;
	}
	/**
	* 获取完成状态0:未完成1：已完成
	*/
	public Byte getCompStatus(){
	    return this.compStatus;
	}
	/**
	* 设置完成状态0:未完成1：已完成
	* @param compStatus
	*/
	public void setCompStatus(Byte compStatus){
	    this.compStatus = compStatus;
	}
	/**
	* 获取预计发货日期
	*/
	public Date getPreSendDate(){
	    return this.preSendDate;
	}
	/**
	* 设置预计发货日期
	* @param preSendDate
	*/
	public void setPreSendDate(Date preSendDate){
	    this.preSendDate = preSendDate;
	}
	/**
	* 获取备注
	*/
	public String getRemarks(){
	    return this.remarks;
	}
	/**
	* 设置备注
	* @param remarks
	*/
	public void setRemarks(String remarks){
	    this.remarks = remarks;
	}
	/**
	* 获取0正常， 1 已删除
	*/
	public Byte getEnable(){
	    return this.enable;
	}
	/**
	* 设置0正常， 1 已删除
	* @param enable
	*/
	public void setEnable(Byte enable){
	    this.enable = enable;
	}

}
