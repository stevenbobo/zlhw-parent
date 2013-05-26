package com.zb.jnlxc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_DeliveryRecord")
@TableDeclare(comment = "发货记录表", tableName = "DeliveryRecord")
public class DeliveryRecord extends HIntTable{
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate; //发货日期
	@ManyToOne
	private OrderDetail orderDetail; //订单明细，记录订购的产品信息
	@ManyToOne
	private Admin charge; //负责人
	@ManyToOne
	private Admin admin; //操作管理员
	private Float sendQuantity; //发货重量
	@Version  
    private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getDeliveryDate(){
	    return this.deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate){
	    this.deliveryDate = deliveryDate;
	}
	public OrderDetail getOrderDetail(){
	    return this.orderDetail;
	}
	public void setOrderDetail(OrderDetail orderDetail){
	    this.orderDetail = orderDetail;
	}
	public Admin getCharge(){
	    return this.charge;
	}
	public void setCharge(Admin charge){
	    this.charge = charge;
	}
	public Admin getAdmin(){
	    return this.admin;
	}
	public void setAdmin(Admin admin){
	    this.admin = admin;
	}
	public Float getSendQuantity(){
	    return this.sendQuantity;
	}
	public void setSendQuantity(Float sendQuantity){
	    this.sendQuantity = sendQuantity;
	}

}
