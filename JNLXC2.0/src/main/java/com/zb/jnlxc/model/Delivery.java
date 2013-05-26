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
@Table(name="JNLXC_Delivery")
@TableDeclare(comment = "发货表", tableName = "Delivery")
public class Delivery extends HIntTable{
	@Column(length=100)
	private String setSize; //规定尺寸
	@ManyToOne
	private Scheme scheme; //图纸号
	@ManyToOne
	private Admin deliver; //发货员
	@ManyToOne
	private Admin warehouser; //仓库管理员
	@ManyToOne
	private Admin productioner; //生产部管理员
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate; //发货日期
	private Integer deliveryWeight; //发货重量
	private Integer deliveryQuantity; //发货数量
	@Version  
    private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
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
	* 获取图纸号
	*/
	public Scheme getScheme(){
	    return this.scheme;
	}
	/**
	* 设置图纸号
	* @param scheme
	*/
	public void setScheme(Scheme scheme){
	    this.scheme = scheme;
	}
	/**
	* 获取发货员
	*/
	public Admin getDeliver(){
	    return this.deliver;
	}
	/**
	* 设置发货员
	* @param deliver
	*/
	public void setDeliver(Admin deliver){
	    this.deliver = deliver;
	}
	/**
	* 获取仓库管理员
	*/
	public Admin getWarehouser(){
	    return this.warehouser;
	}
	/**
	* 设置仓库管理员
	* @param warehouser
	*/
	public void setWarehouser(Admin warehouser){
	    this.warehouser = warehouser;
	}
	/**
	* 获取生产部管理员
	*/
	public Admin getProductioner(){
	    return this.productioner;
	}
	/**
	* 设置生产部管理员
	* @param productioner
	*/
	public void setProductioner(Admin productioner){
	    this.productioner = productioner;
	}
	/**
	* 获取发货日期
	*/
	public Date getDeliveryDate(){
	    return this.deliveryDate;
	}
	/**
	* 设置发货日期
	* @param deliveryDate
	*/
	public void setDeliveryDate(Date deliveryDate){
	    this.deliveryDate = deliveryDate;
	}
	/**
	* 获取发货重量
	*/
	public Integer getDeliveryWeight(){
	    return this.deliveryWeight;
	}
	/**
	* 设置发货重量
	* @param deliveryWeight
	*/
	public void setDeliveryWeight(Integer deliveryWeight){
	    this.deliveryWeight = deliveryWeight;
	}
	/**
	* 获取发货数量
	*/
	public Integer getDeliveryQuantity(){
	    return this.deliveryQuantity;
	}
	/**
	* 设置发货数量
	* @param deliveryQuantity
	*/
	public void setDeliveryQuantity(Integer deliveryQuantity){
	    this.deliveryQuantity = deliveryQuantity;
	}


}
