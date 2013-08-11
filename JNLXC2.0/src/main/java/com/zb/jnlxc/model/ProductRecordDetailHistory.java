package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JNLXC_ProductRecordDetailHistory")
@TableDeclare(comment = "生产记录明细表历史记录", tableName = "ProductRecordDetailHistory")
@Optimistic
public class ProductRecordDetailHistory extends HIntTable {
	private Integer productRecord_dbId; // 生产记录表
	private Integer orderDetail_dbId; // 订单明细，记录订购的产品信息
    private Integer operater_dbId;
    @Column(length=100)
    private String operater_Name;
    @Column(length=50)
    private String setSize; //规定尺寸
    @Column(length=100)
    private String taskName;
	private Integer detailWeight; // 相对于订单明细中的生产重量统计
	private Integer detailQuantity; // 相对于订单明细中的生产数量统计
    @Temporal(TemporalType.TIMESTAMP)
    private Date operateDate;
    @Column(length=1024)
    private String wcomment; //备注

    public String getWcomment() {
        return wcomment;
    }

    public void setWcomment(String wcomment) {
        this.wcomment = wcomment;
    }

    public String getSetSize() {
        return setSize;
    }

    public void setSetSize(String setSize) {
        this.setSize = setSize;
    }

    public Integer getProductRecord_dbId() {
        return productRecord_dbId;
    }

    public void setProductRecord_dbId(Integer productRecord_dbId) {
        this.productRecord_dbId = productRecord_dbId;
    }

    public Integer getOrderDetail_dbId() {
        return orderDetail_dbId;
    }

    public void setOrderDetail_dbId(Integer orderDetail_dbId) {
        this.orderDetail_dbId = orderDetail_dbId;
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

    public Integer getOperater_dbId() {
        return operater_dbId;
    }

    public void setOperater_dbId(Integer operater_dbId) {
        this.operater_dbId = operater_dbId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperater_Name() {
        return operater_Name;
    }

    public void setOperater_Name(String operater_Name) {
        this.operater_Name = operater_Name;
    }
}
