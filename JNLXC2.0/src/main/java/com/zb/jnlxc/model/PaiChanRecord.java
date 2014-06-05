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

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
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

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public OrderForm getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
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
    public void setCurrentState(byte currentState) {
        this.currentState = currentState;
    }

    public byte getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Byte currentState) {
        this.currentState = currentState;
    }



    public void setWcomment(String wcomment) {
        this.wcomment = wcomment;
    }

    public String getWcomment() {
        return wcomment;
    }
    @Override
    public String toString() {
        return "PaiChanRecord{" +
                "orderForm=" + orderForm +
                ", code='" + code + '\'' +
                ", orderCodes='" + orderCodes + '\'' +
                ", type='" + type + '\'' +
                ", productTeam=" + productTeam +
                ", mouldIds='" + mouldIds + '\'' +
                ", mouldCodes='" + mouldCodes + '\'' +
                ", CreateDate=" + CreateDate +
                ", enable=" + enable +
                ", recordNum=" + recordNum +
                ", charger=" + charger +
                ", currentState=" + currentState +
                ", wcomment='" + wcomment + '\'' +
                '}';
    }

    private Float kpm; //米重
    @ManyToOne
    private Admin aginger; //时效员
    @Column(length=50)
    private String aginghardness; //时效实测硬度
    @Column(length=50)
    private String alHeatNum; //铝棒炉号
    @ManyToOne
    private Admin packager; //包装员
    @ManyToOne
    private DataDict pack; //包装方式表
    @ManyToOne
    private Admin oxifilmer; //氧化员
    @Column(length=20)
    private String oxiFilm; //氧化膜厚度
    @ManyToOne
    private Admin weighter; //过磅员

    @ManyToOne
    private Admin transiter; //过磅员

    private Integer nitrideNum; //氮化数量
    @ManyToOne
    private Admin warehouser; //仓库管理员
    @Column(length=100)
    private String storeLocation; //存放位置
    @ManyToOne
    private Admin electrophoresis; //电泳员
    @Column(length=50)
    private String DoEletr; //电泳记录
    @ManyToOne
    private Admin sender; //发货员
    @ManyToOne
    private Admin plater; //喷涂员
    @Column(length=50)
    private String finalWeight; //发货重量
    @ManyToOne
    private Admin financer; //财务
    private Integer totalPrice; //总价格

    public Float getKpm() {
        return kpm;
    }

    public void setKpm(Float kpm) {
        this.kpm = kpm;
    }

    public Admin getAginger() {
        return aginger;
    }

    public void setAginger(Admin aginger) {
        this.aginger = aginger;
    }

    public String getAginghardness() {
        return aginghardness;
    }

    public void setAginghardness(String aginghardness) {
        this.aginghardness = aginghardness;
    }

    public String getAlHeatNum() {
        return alHeatNum;
    }

    public void setAlHeatNum(String alHeatNum) {
        this.alHeatNum = alHeatNum;
    }

    public Admin getPackager() {
        return packager;
    }

    public void setPackager(Admin packager) {
        this.packager = packager;
    }

    public DataDict getPack() {
        return pack;
    }

    public void setPack(DataDict pack) {
        this.pack = pack;
    }

    public Admin getOxifilmer() {
        return oxifilmer;
    }

    public void setOxifilmer(Admin oxifilmer) {
        this.oxifilmer = oxifilmer;
    }

    public String getOxiFilm() {
        return oxiFilm;
    }

    public void setOxiFilm(String oxiFilm) {
        this.oxiFilm = oxiFilm;
    }

    public Admin getWeighter() {
        return weighter;
    }

    public void setWeighter(Admin weighter) {
        this.weighter = weighter;
    }

    public Integer getNitrideNum() {
        return nitrideNum;
    }

    public void setNitrideNum(Integer nitrideNum) {
        this.nitrideNum = nitrideNum;
    }

    public Admin getWarehouser() {
        return warehouser;
    }

    public void setWarehouser(Admin warehouser) {
        this.warehouser = warehouser;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public Admin getElectrophoresis() {
        return electrophoresis;
    }

    public void setElectrophoresis(Admin electrophoresis) {
        this.electrophoresis = electrophoresis;
    }

    public String getDoEletr() {
        return DoEletr;
    }

    public void setDoEletr(String doEletr) {
        DoEletr = doEletr;
    }

    public Admin getSender() {
        return sender;
    }

    public void setSender(Admin sender) {
        this.sender = sender;
    }

    public Admin getPlater() {
        return plater;
    }

    public void setPlater(Admin plater) {
        this.plater = plater;
    }

    public String getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(String finalWeight) {
        this.finalWeight = finalWeight;
    }

    public Admin getFinancer() {
        return financer;
    }

    public void setFinancer(Admin financer) {
        this.financer = financer;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Admin getTransiter() {
        return transiter;
    }

    public void setTransiter(Admin transiter) {
        this.transiter = transiter;
    }
}
