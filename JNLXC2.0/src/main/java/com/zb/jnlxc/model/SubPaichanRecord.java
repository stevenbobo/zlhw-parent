package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by apple on 2014/7/20.
 */
@Entity
@Table(name="JNLXC_SubPaichanRecord")
@TableDeclare(comment = "氧化记录", tableName = "SubPaichanRecord")
public class SubPaichanRecord extends HIntTable {
    @ManyToOne
    private PaiChanRecord paiChanRecord; //排产记录

    private Integer lastProcessId;//上道工序编号
    @Column(length=100)
    private String code;//上道工序编号_shortName+dbid

    private Byte process;//类型氧化/时效/喷涂

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
    private Admin transiter; //中转员

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

    @Column(length=1024)
    private String wcomment; //备注


    public PaiChanRecord getPaiChanRecord() {
        return paiChanRecord;
    }

    public void setPaiChanRecord(PaiChanRecord paiChanRecord) {
        this.paiChanRecord = paiChanRecord;
    }

    public Integer getLastProcessId() {
        return lastProcessId;
    }

    public void setLastProcessId(Integer lastProcessId) {
        this.lastProcessId = lastProcessId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getProcess() {
        return process;
    }

    public void setProcess(Byte process) {
        this.process = process;
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

    public Admin getTransiter() {
        return transiter;
    }

    public void setTransiter(Admin transiter) {
        this.transiter = transiter;
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

    public String getWcomment() {
        return wcomment;
    }

    public void setWcomment(String wcomment) {
        this.wcomment = wcomment;
    }
}
