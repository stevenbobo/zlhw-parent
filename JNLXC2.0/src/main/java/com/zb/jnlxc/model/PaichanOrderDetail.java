package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="JNLXC_PaichanOrderDetail")
@TableDeclare(comment = "排产订单明细", tableName = "PaichanOrderDetail")
@Optimistic
public class PaichanOrderDetail extends HIntTable{
	@ManyToOne
	private OrderDetail orderDetail; //订单表
    @ManyToOne
    private PaiChanRecord paichanRecord;// 排产记录

    @Column(length=50)
    private String setSize; //规定尺寸
    private Integer orderQuantity; //订货数量
    private Integer orderWeight; //订货重量（千克）
    private Integer orderTotalMeter; //总米数（米）
    private Integer compQuantity; //完成数量
    private Integer compWeight; //完成重量（千克）
    private Byte compStatus; //完成状态0:未完成1：已完成
    @Temporal(TemporalType.TIMESTAMP)
    private Date preSendDate; //预计发货日期
    @Column(length=1024)
    private String remarks; //备注
    private Byte enable; //0正常， 1 已删除

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public PaiChanRecord getPaichanRecord() {
        return paichanRecord;
    }

    public void setPaichanRecord(PaiChanRecord paichanRecord) {
        this.paichanRecord = paichanRecord;
    }

    public String getSetSize() {
        return setSize;
    }

    public void setSetSize(String setSize) {
        this.setSize = setSize;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Integer getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(Integer orderWeight) {
        this.orderWeight = orderWeight;
    }

    public Integer getOrderTotalMeter() {
        return orderTotalMeter;
    }

    public void setOrderTotalMeter(Integer orderTotalMeter) {
        this.orderTotalMeter = orderTotalMeter;
    }

    public Integer getCompWeight() {
        return compWeight;
    }

    public void setCompWeight(Integer compWeight) {
        this.compWeight = compWeight;
    }

    public Byte getCompStatus() {
        return compStatus;
    }

    public void setCompStatus(Byte compStatus) {
        this.compStatus = compStatus;
    }

    public Date getPreSendDate() {
        return preSendDate;
    }

    public void setPreSendDate(Date preSendDate) {
        this.preSendDate = preSendDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    public Integer getCompQuantity() {
        return compQuantity;
    }

    public void setCompQuantity(Integer compQuantity) {
        this.compQuantity = compQuantity;
    }
}
