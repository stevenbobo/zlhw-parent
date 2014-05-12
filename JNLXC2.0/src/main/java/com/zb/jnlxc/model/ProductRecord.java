package com.zb.jnlxc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_ProductRecord")
@TableDeclare(comment = "生产记录表", tableName = "ProductRecord")
public class ProductRecord extends HIntTable{
	@ManyToOne
	private OrderForm orderForm; //订单表
	@Column(length=50)
	private String code; //生产记录编码 订单号+订单的第n个生产记录
	private Integer recordNum; //该订单的第n次记录
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate; //生产日期
	@ManyToOne
	private Admin charger; //机台负责人
	@ManyToOne
	private ProductTeam productTeam; //机台班组
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
	private DataDict surfaceProcess; //表面处理表
	@ManyToOne
	private Admin weighter; //过磅员
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
	private Byte currentState; //当前状态：0:正常1:流程中:
	private Byte enable; //0正常， 1 已删除
	@Version  
    private Integer version;
    @Column(length=50)
    private String nextStep;//下道工序
    @Column(length=50)
    private String currentStep;//当前工序
	/**
	* 获取订单表
	*/
	public OrderForm getOrderForm(){
	    return this.orderForm;
	}
	/**
	* 设置订单表
	* @param orderForm
	*/
	public void setOrderForm(OrderForm orderForm){
	    this.orderForm = orderForm;
	}
	/**
	* 获取生产记录编码 订单号+订单的第n个生产记录
	*/
	public String getCode(){
	    return this.code;
	}
	/**
	* 设置生产记录编码 订单号+订单的第n个生产记录
	* @param code
	*/
	public void setCode(String code){
	    this.code = code;
	}
	/**
	* 获取该订单的第n次记录
	*/
	public Integer getRecordNum(){
	    return this.recordNum;
	}
	/**
	* 设置该订单的第n次记录
	* @param recordNum
	*/
	public void setRecordNum(Integer recordNum){
	    this.recordNum = recordNum;
	}
	/**
	* 获取生产日期
	*/
	public Date getCreateDate(){
	    return this.createDate;
	}
	/**
	* 设置生产日期
	* @param createDate
	*/
	public void setCreateDate(Date createDate){
	    this.createDate = createDate;
	}
	/**
	* 获取机台负责人
	*/
	public Admin getCharger(){
	    return this.charger;
	}
	/**
	* 设置机台负责人
	* @param charger
	*/
	public void setCharger(Admin charger){
	    this.charger = charger;
	}
	/**
	* 获取机台班组
	*/
	public ProductTeam getProductTeam(){
	    return this.productTeam;
	}
	/**
	* 设置机台班组
	* @param productTeam
	*/
	public void setProductTeam(ProductTeam productTeam){
	    this.productTeam = productTeam;
	}
	/**
	* 获取米重
	*/
	public Float getKpm(){
	    return this.kpm;
	}
	/**
	* 设置米重
	* @param kpm
	*/
	public void setKpm(Float kpm){
	    this.kpm = kpm;
	}
	/**
	* 获取时效员
	*/
	public Admin getAginger(){
	    return this.aginger;
	}
	/**
	* 设置时效员
	* @param aginger
	*/
	public void setAginger(Admin aginger){
	    this.aginger = aginger;
	}
	/**
	* 获取时效实测硬度
	*/
	public String getAginghardness(){
	    return this.aginghardness;
	}
	/**
	* 设置时效实测硬度
	* @param aginghardness
	*/
	public void setAginghardness(String aginghardness){
	    this.aginghardness = aginghardness;
	}
	/**
	* 获取铝棒炉号
	*/
	public String getAlHeatNum(){
	    return this.alHeatNum;
	}
	/**
	* 设置铝棒炉号
	* @param alHeatNum
	*/
	public void setAlHeatNum(String alHeatNum){
	    this.alHeatNum = alHeatNum;
	}
	/**
	* 获取包装员
	*/
	public Admin getPackager(){
	    return this.packager;
	}
	/**
	* 设置包装员
	* @param packager
	*/
	public void setPackager(Admin packager){
	    this.packager = packager;
	}
	/**
	* 获取包装方式表
	*/
	public DataDict getPack(){
	    return this.pack;
	}
	/**
	* 设置包装方式表
	* @param pack
	*/
	public void setPack(DataDict pack){
	    this.pack = pack;
	}
	/**
	* 获取氧化员
	*/
	public Admin getOxifilmer(){
	    return this.oxifilmer;
	}
	/**
	* 设置氧化员
	* @param oxifilmer
	*/
	public void setOxifilmer(Admin oxifilmer){
	    this.oxifilmer = oxifilmer;
	}
	/**
	* 获取氧化膜厚度
	*/
	public String getOxiFilm(){
	    return this.oxiFilm;
	}
	/**
	* 设置氧化膜厚度
	* @param oxiFilm
	*/
	public void setOxiFilm(String oxiFilm){
	    this.oxiFilm = oxiFilm;
	}
	/**
	* 获取表面处理表
	*/
	public DataDict getSurfaceProcess(){
	    return this.surfaceProcess;
	}
	/**
	* 设置表面处理表
	* @param surfaceProcess
	*/
	public void setSurfaceProcess(DataDict surfaceProcess){
	    this.surfaceProcess = surfaceProcess;
	}
	/**
	* 获取过磅员
	*/
	public Admin getWeighter(){
	    return this.weighter;
	}
	/**
	* 设置过磅员
	* @param weighter
	*/
	public void setWeighter(Admin weighter){
	    this.weighter = weighter;
	}
	/**
	* 获取氮化数量
	*/
	public Integer getNitrideNum(){
	    return this.nitrideNum;
	}
	/**
	* 设置氮化数量
	* @param nitrideNum
	*/
	public void setNitrideNum(Integer nitrideNum){
	    this.nitrideNum = nitrideNum;
	}
	/**
	* 获取仓库管理员
	*/
	public Admin getWarehouser(){
	    return this.warehouser;
	}
	/**
	* 设置仓库管理员
	* @param warehouser
	*/
	public void setWarehouser(Admin warehouser){
	    this.warehouser = warehouser;
	}
	/**
	* 获取存放位置
	*/
	public String getStoreLocation(){
	    return this.storeLocation;
	}
	/**
	* 设置存放位置
	* @param storeLocation
	*/
	public void setStoreLocation(String storeLocation){
	    this.storeLocation = storeLocation;
	}
	/**
	* 获取电泳员
	*/
	public Admin getElectrophoresis(){
	    return this.electrophoresis;
	}
	/**
	* 设置电泳员
	* @param electrophoresis
	*/
	public void setElectrophoresis(Admin electrophoresis){
	    this.electrophoresis = electrophoresis;
	}
	/**
	* 获取电泳记录
	*/
	public String getDoEletr(){
	    return this.DoEletr;
	}
	/**
	* 设置电泳记录
	* @param DoEletr
	*/
	public void setDoEletr(String DoEletr){
	    this.DoEletr = DoEletr;
	}
	/**
	* 获取发货员
	*/
	public Admin getSender(){
	    return this.sender;
	}
	/**
	* 设置发货员
	* @param sender
	*/
	public void setSender(Admin sender){
	    this.sender = sender;
	}
	/**
	* 获取喷涂员
	*/
	public Admin getPlater(){
	    return this.plater;
	}
	/**
	* 设置喷涂员
	* @param plater
	*/
	public void setPlater(Admin plater){
	    this.plater = plater;
	}
	/**
	* 获取发货重量
	*/
	public String getFinalWeight(){
	    return this.finalWeight;
	}
	/**
	* 设置发货重量
	* @param finalWeight
	*/
	public void setFinalWeight(String finalWeight){
	    this.finalWeight = finalWeight;
	}
	/**
	* 获取财务
	*/
	public Admin getFinancer(){
	    return this.financer;
	}
	/**
	* 设置财务
	* @param financer
	*/
	public void setFinancer(Admin financer){
	    this.financer = financer;
	}
	/**
	* 获取总价格
	*/
	public Integer getTotalPrice(){
	    return this.totalPrice;
	}
	/**
	* 设置总价格
	* @param totalPrice
	*/
	public void setTotalPrice(Integer totalPrice){
	    this.totalPrice = totalPrice;
	}
	/**
	* 获取备注
	*/
	public String getWcomment(){
	    return this.wcomment;
	}
	/**
	* 设置备注
	* @param wcomment
	*/
	public void setWcomment(String wcomment){
	    this.wcomment = wcomment;
	}
	/**
	* 获取当前状态：0:正常1:流程中:
	*/
	public Byte getCurrentState(){
	    return this.currentState;
	}
	/**
	* 设置当前状态：0:正常1:流程中:
	* @param currentState
	*/
	public void setCurrentState(Byte currentState){
	    this.currentState = currentState;
	}
	/**
	* 获取0正常， 1 已删除
	*/
	public Byte getEnable(){
	    return this.enable;
	}
	/**
	* 设置0正常， 1 已删除
	* @param enable
	*/
	public void setEnable(Byte enable){
	    this.enable = enable;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
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
}
