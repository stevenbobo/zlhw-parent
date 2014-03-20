package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhengbo.zb
 * Date: 14-2-4
 * Time: 下午7:19
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="JNLXC_PaiChanRecord")
@TableDeclare(comment = "排产记录表", tableName = "PaiChanRecord")
public class PaiChanRecord extends HIntTable {
    @Column(length=250)
    private String orderIds; //订单编号
    @Column(length=250)
    private String code;//排产编号
    @Column(length=250)
    private String orderCodes;//订单编码列表

    private String type;//normal/combine/splite
    @ManyToOne
    private ProductTeam productTeam;//机台编号
    @Column(length=250)
    private String mouldIds;//模具列表
    @Column(length=250)
    private String mouldCodes;//模具列表
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime; //生产日期

    private Byte enable = 1; //已经开启排摸 0:已经排产 1:未排产

    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    public ProductTeam getProductTeam() {
        return productTeam;
    }

    public void setProductTeam(ProductTeam productTeam) {
        this.productTeam = productTeam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public String getOrderCodes() {
        return orderCodes;
    }

    public void setOrderCodes(String orderCodes) {
        this.orderCodes = orderCodes;
    }

    public String getMouldIds() {
        return mouldIds;
    }

    public void setMouldIds(String mouldIds) {
        this.mouldIds = mouldIds;
    }

    public String getMouldCodes() {
        return mouldCodes;
    }

    public void setMouldCodes(String mouldCodes) {
        this.mouldCodes = mouldCodes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PaiChanRecord{" +
                "orderIds='" + orderIds + '\'' +
                ", code='" + code + '\'' +
                ", orderCodes='" + orderCodes + '\'' +
                ", type='" + type + '\'' +
                ", productTeam=" + productTeam +
                ", mouldIds='" + mouldIds + '\'' +
                ", mouldCodes='" + mouldCodes + '\'' +
                ", createTime=" + createTime +
                ", enable=" + enable +
                '}';
    }
}
