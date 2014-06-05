package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhengbo.zb on 2014/6/3.
 */
@Entity
@Table(name="JNLXC_DistributionRecord")
@TableDeclare(comment = "胚料分配记录表", tableName = "DistributionRecord")
public class DistributionRecord extends HIntTable {
    @ManyToOne
    private OrderDetail orderDetail; //订单明细，记录订购的产品信息
    @ManyToOne
    private RemaindProduct remaindProduct;

    private Integer quantity;

    private Integer weight;

    @ManyToOne
    private Admin Operator;//操作员

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建日期

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public RemaindProduct getRemaindProduct() {
        return remaindProduct;
    }

    public void setRemaindProduct(RemaindProduct remaindProduct) {
        this.remaindProduct = remaindProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Admin getOperator() {
        return Operator;
    }

    public void setOperator(Admin operator) {
        Operator = operator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
