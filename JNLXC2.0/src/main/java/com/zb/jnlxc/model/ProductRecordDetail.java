package com.zb.jnlxc.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name = "JNLXC_ProductRecordDetail")
@TableDeclare(comment = "生产记录明细表", tableName = "ProductRecordDetail")
@Optimistic
public class ProductRecordDetail extends HIntTable {
	@ManyToOne
	private ProductRecord productRecord; // 生产记录表
	@ManyToOne
	private OrderDetail orderDetail; // 订单明细，记录订购的产品信息
	private Integer detailWeight; // 相对于订单明细中的生产重量统计
	private Integer detailQuantity; // 相对于订单明细中的生产数量统计
	@Version  
    private Integer version;

	/**
	 * 获取生产记录表
	 */
	public ProductRecord getProductRecord() {
		return this.productRecord;
	}

	/**
	 * 设置生产记录表
	 * 
	 * @param productRecord
	 */
	public void setProductRecord(ProductRecord productRecord) {
		this.productRecord = productRecord;
	}

	/**
	 * 获取订单明细，记录订购的产品信息
	 */
	public OrderDetail getOrderDetail() {
		return this.orderDetail;
	}

	/**
	 * 设置订单明细，记录订购的产品信息
	 * 
	 * @param orderDetail
	 */
	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	/**
	 * 获取相对于订单明细中的生产重量统计
	 */
	public Integer getDetailWeight() {
		return this.detailWeight;
	}

	/**
	 * 设置相对于订单明细中的生产重量统计
	 * 
	 * @param detailWeight
	 */
	public void setDetailWeight(Integer detailWeight) {
		this.detailWeight = detailWeight;
	}

	/**
	 * 获取相对于订单明细中的生产数量统计
	 */
	public Integer getDetailQuantity() {
		return this.detailQuantity;
	}

	/**
	 * 设置相对于订单明细中的生产数量统计
	 * 
	 * @param detailQuantity
	 */
	public void setDetailQuantity(Integer detailQuantity) {
		this.detailQuantity = detailQuantity;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
