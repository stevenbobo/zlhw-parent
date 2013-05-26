package com.zb.jnlxc.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_OrderForm_Author")
@TableDeclare(comment = "订单_权限表", tableName = "OrderForm_Author")
public class OrderForm_Author extends HIntTable{
	@ManyToOne
	private Admin ownerAdmin; //创建订单的业务员---订单拥有者
	@ManyToOne
	private Admin authorizedAdmin; //被授权的业务员
	@Version  
    private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	* 获取创建订单的业务员---订单拥有者
	*/
	public Admin getOwnerAdmin(){
	    return this.ownerAdmin;
	}
	/**
	* 设置创建订单的业务员---订单拥有者
	* @param ownerAdmin
	*/
	public void setOwnerAdmin(Admin ownerAdmin){
	    this.ownerAdmin = ownerAdmin;
	}
	/**
	* 获取被授权的业务员
	*/
	public Admin getAuthorizedAdmin(){
	    return this.authorizedAdmin;
	}
	/**
	* 设置被授权的业务员
	* @param authorizedAdmin
	*/
	public void setAuthorizedAdmin(Admin authorizedAdmin){
	    this.authorizedAdmin = authorizedAdmin;
	}

}
