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
    @ManyToOne
    private OrderForm orderForm; //订单编号
    @Column(length=100)
    private String code;//排产编号
    @Column(length=250)
    private String orderCodes;//订单编码列表
    @Column(length=50)
    private String type;//normal/combine/splite
    @ManyToOne
    private ProductTeam productTeam;//机台编号
    @Column(length=250)
    private String mouldIds;//模具列表
    @Column(length=250)
    private String mouldCodes;//模具列表
    @Temporal(TemporalType.TIMESTAMP)
    private Date CreateDate; //生产日期

    private Byte enable = 1; //已经开启排摸 0:已经排产 1:未排产

    private Integer recordNum; //该订单的第n次排产

    @ManyToOne
    private Admin charger; //机台负责人
    private Byte currentState;//当前状态：0:已完成1:流程中:
    @Column(length=1024)
    private String wcomment; //备注

    @Column(length=50)
    private String nextStep;//下道工序
    @Column(length=50)
    private String currentStep;//当前工序

    private Boolean distributeFinished;

    public OrderForm getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderCodes() {
        return orderCodes;
    }

    public void setOrderCodes(String orderCodes) {
        this.orderCodes = orderCodes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProductTeam getProductTeam() {
        return productTeam;
    }

    public void setProductTeam(ProductTeam productTeam) {
        this.productTeam = productTeam;
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

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    public Integer getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Integer recordNum) {
        this.recordNum = recordNum;
    }

    public Admin getCharger() {
        return charger;
    }

    public void setCharger(Admin charger) {
        this.charger = charger;
    }

    public Byte getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Byte currentState) {
        this.currentState = currentState;
    }

    public String getWcomment() {
        return wcomment;
    }

    public void setWcomment(String wcomment) {
        this.wcomment = wcomment;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public Boolean getDistributeFinished() {
        return distributeFinished;
    }

    public void setDistributeFinished(Boolean distributeFinished) {
        this.distributeFinished = distributeFinished;
    }

}
