package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;
import com.zb.util.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="JNLXC_OrderForm")
@TableDeclare(comment = "订单表", tableName = "OrderForm")
public class OrderForm extends HIntTable{
	@Column(length=40)
	private String code; //订单编号
	private Integer nextRecordNum; //订单的下一条生产记录号 
	@ManyToOne
	private DataDict material; //材质表
	@ManyToOne
	private Client client; //客户表
//	@ManyToOne
//	private Admin agent; //业务员
	@ManyToOne
	private Admin kaidan; //开单员
	@ManyToOne
	private Scheme scheme; //图纸表
	@ManyToOne
	private DataDict pack; //包装方式表
	@ManyToOne
	private City city; //送货城市
	@Column(length=80)
	private String address; //送货详细地址
	@Column(length=20)
	private String oxideFilm; //氧化膜厚度
	@ManyToOne
	private DataDict surfaceProcess; //表面处理表
	@Column(length=50)
	private String hardness; //材料硬度
	@Column(length=50)
	private String doEletr; //电泳记录
	@Column(length=100)
	private String path; //步骤1
	private Float kpm; //米重 克/米
    //(alPrice+processingFee)/1000=m_price
	private BigDecimal alPrice; //铝锭价格 元/吨
    private BigDecimal processingFee; //加工费 元
    private BigDecimal m_price; //材料单价费
    private BigDecimal workFee; //上机费
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate; //下订单日期
    @Temporal(TemporalType.TIMESTAMP)
	private Date registerDate; //登记日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline; //期限
    @Temporal(TemporalType.TIMESTAMP)
    private Date factSendDate; //实际发货日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date preSendDate; //预计发货日期
    private Byte compStatus; //完成状态0:未完成1：已完成
	private Integer totalWeight; //总重量,根据明细统计得出
	private Integer compWeight; //完成重量
    private Integer compQuantity; //完成数量
	@Column(length=1024)
	private String mcomment; //备注

    private Byte shixiao; //是否时效：{id:0,text:'否'}, {id:1, text: '是'}
	private Byte includeTax; //是否含税：{id:0,text:'税前'}, {id:1, text: '税后'}
    private Byte bill; //是否开票 {id:0,text:'开票'}, {id:1, text: '不开票'}
    private Byte payWay; //付款方式{id:0,text:'现金'}, {id:1, text: '月结'},{id:1, text: '特殊月结'}
    private Byte pickWay; //提货方式{id:0,text:'送货上门'}, {id:1, text: '自提货'}

    private Byte currentState; //:
    private Byte enable; //0正常， 1 已删除
    @Version
    private Integer version;
	/**
	* 获取订单编号
	*/
	public String getCode(){
	    return this.code;
	}
	/**
	* 设置订单编号
	* @param code
	*/
	public void setCode(String code){
	    this.code = code;
	}
	/**
	* 获取订单的下一条生产记录号 
	*/
	public Integer getNextRecordNum(){
	    return this.nextRecordNum;
	}
	/**
	* 设置订单的下一条生产记录号 
	* @param nextRecordNum
	*/
	public void setNextRecordNum(Integer nextRecordNum){
	    this.nextRecordNum = nextRecordNum;
	}
	/**
	* 获取材质表
	*/
	public DataDict getMaterial(){
	    return this.material;
	}
	/**
	* 设置材质表
	* @param material
	*/
	public void setMaterial(DataDict material){
	    this.material = material;
	}
	/**
	* 获取客户表
	*/
	public Client getClient(){
	    return this.client;
	}
	/**
	* 设置客户表
	* @param client
	*/
	public void setClient(Client client){
	    this.client = client;
	}
	/**
	* 获取开单员
	*/
	public Admin getKaidan(){
	    return this.kaidan;
	}
	/**
	* 设置开单员
	* @param kaidan
	*/
	public void setKaidan(Admin kaidan){
	    this.kaidan = kaidan;
	}
	/**
	* 获取图纸表
	*/
	public Scheme getScheme(){
	    return this.scheme;
	}
	/**
	* 设置图纸表
	* @param scheme
	*/
	public void setScheme(Scheme scheme){
	    this.scheme = scheme;
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
	* 获取送货城市
	*/
	public City getCity(){
	    return this.city;
	}
	/**
	* 设置送货城市
	* @param city
	*/
	public void setCity(City city){
	    this.city = city;
	}
	/**
	* 获取送货详细地址
	*/
	public String getAddress(){
	    return this.address;
	}
	/**
	* 设置送货详细地址
	* @param address
	*/
	public void setAddress(String address){
	    this.address = address;
	}
	/**
	* 获取氧化膜厚度
	*/
	public String getOxideFilm(){
	    return this.oxideFilm;
	}
	/**
	* 设置氧化膜厚度
	* @param oxideFilm
	*/
	public void setOxideFilm(String oxideFilm){
	    this.oxideFilm = oxideFilm;
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
	* 获取材料硬度
	*/
	public String getHardness(){
	    return this.hardness;
	}
	/**
	* 设置材料硬度
	* @param hardness
	*/
	public void setHardness(String hardness){
	    this.hardness = hardness;
	}
	/**
	* 获取电泳记录
	*/
	public String getDoEletr(){
	    return this.doEletr;
	}
	/**
	* 设置电泳记录
	* @param doEletr
	*/
	public void setDoEletr(String doEletr){
	    this.doEletr = doEletr;
	}
	/**
	* 获取步骤1
	*/
	public String getPath(){
	    return this.path;
	}
	/**
	* 设置步骤
	* @param path
	*/
	public void setPath(String path){
	    this.path = path;
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
	* 获取下订单日期
	*/
	public Date getOrderDate(){
	    return this.orderDate;
	}
	/**
	* 设置下订单日期
	* @param orderDate
	*/
	public void setOrderDate(Date orderDate){
	    this.orderDate = orderDate;
	}
	/**
	* 获取铝锭价格
	*/
	public BigDecimal getAlPrice(){
	    return this.alPrice;
	}
	/**
	* 设置铝锭价格
	* @param alPrice
	*/
	public void setAlPrice(BigDecimal alPrice){
	    this.alPrice = alPrice;
	}
	/**
	* 获取加工费
	*/
	public BigDecimal getProcessingFee(){
	    return this.processingFee;
	}
	/**
	* 设置加工费
	* @param processingFee
	*/
	public void setProcessingFee(BigDecimal processingFee){
	    this.processingFee = processingFee;
	}
	/**
	* 获取材料单价费
	*/
	public BigDecimal getM_price(){
	    return this.m_price;
	}
	/**
	* 设置材料单价费
	* @param m_price
	*/
	public void setM_price(BigDecimal m_price){
	    this.m_price = m_price;
	}
	/**
	* 获取登记日期
	*/
	public Date getRegisterDate(){
	    return this.registerDate;
	}
	/**
	* 设置登记日期
	* @param registerDate
	*/
	public void setRegisterDate(Date registerDate){
	    this.registerDate = registerDate;
	}
	/**
	* 获取完成状态0:未完成1：已完成
	*/
	public Byte getCompStatus(){
	    return this.compStatus;
	}
	/**
	* 设置完成状态0:未完成1：已完成
	* @param compStatus
	*/
	public void setCompStatus(Byte compStatus){
	    this.compStatus = compStatus;
	}
	/**
	* 获取总重量,根据明细统计得出
	*/
	public Integer getTotalWeight(){
	    return this.totalWeight;
	}
	/**
	* 设置总重量,根据明细统计得出
	* @param totalWeight
	*/
	public void setTotalWeight(Integer totalWeight){
	    this.totalWeight = totalWeight;
	}

    public Integer getCompWeight() {
        return compWeight;
    }

    public void setCompWeight(Integer compWeight) {
        this.compWeight = compWeight;
    }

    public Integer getCompQuantity() {
        return compQuantity;
    }

    public void setCompQuantity(Integer compQuantity) {
        this.compQuantity = compQuantity;
    }

    /**
	* 获取备注
	*/
	public String getMcomment(){
	    return this.mcomment;
	}
	/**
	* 设置备注
	* @param mcomment
	*/
	public void setMcomment(String mcomment){
	    this.mcomment = mcomment;
	}
	/**
	* 获取期限
	*/
	public Date getDeadline(){
	    return this.deadline;
	}
	/**
	* 设置期限
	* @param deadline
	*/
	public void setDeadline(Date deadline){
	    this.deadline = deadline;
	}
	/**
	* 获取实际发货日期
	*/
	public Date getFactSendDate(){
	    return this.factSendDate;
	}
	/**
	* 设置实际发货日期
	* @param factSendDate
	*/
	public void setFactSendDate(Date factSendDate){
	    this.factSendDate = factSendDate;
	}
	/**
	* 获取预计发货日期
	*/
	public Date getPreSendDate(){
	    return this.preSendDate;
	}
	/**
	* 设置预计发货日期
	* @param preSendDate
	*/
	public void setPreSendDate(Date preSendDate){
	    this.preSendDate = preSendDate;
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
	/**
	* 获取是否含税：税前， 税后
	*/
	public Byte getIncludeTax(){
	    return this.includeTax;
	}
	/**
	* 设置是否含税：税前， 税后
	* @param includeTax
	*/
	public void setIncludeTax(Byte includeTax){
	    this.includeTax = includeTax;
	}
	/**
	* 获取是否开票，0，否；1，是
	*/
	public Byte getBill(){
	    return this.bill;
	}
	/**
	* 设置是否开票，0，否；1，是
	* @param bill
	*/
	public void setBill(Byte bill){
	    this.bill = bill;
	}
	/**
	* 获取付款方式
	*/
	public Byte getPayWay(){
	    return this.payWay;
	}
	/**
	* 设置付款方式
	* @param payWay
	*/
	public void setPayWay(Byte payWay){
	    this.payWay = payWay;
	}
	/**
	* 获取提货方式
	*/
	public Byte getPickWay(){
	    return this.pickWay;
	}
	/**
	* 设置提货方式
	* @param pickWay
	*/
	public void setPickWay(Byte pickWay){
	    this.pickWay = pickWay;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

    public Byte getShixiao() {
        return shixiao;
    }

    public void setShixiao(Byte shixiao) {
        this.shixiao = shixiao;
    }

    public BigDecimal getWorkFee() {
		return workFee;
	}
	public void setWorkFee(BigDecimal workFee) {
		this.workFee = workFee;
	}
	public String getSteps(){
        String result="";
        List<String> l = generateStepList();
        for(String step:l){
            result+="->"+step;
        }
        return result;
    }

    public List<String> generateStepList(){
        List<String> squence=new ArrayList<String>();
        if(getShixiao()==1)
            squence.add("时效");
        if(StringUtils.isNotEmpty(surfaceProcess.getStr1()))
            squence.add(surfaceProcess.getStr1());
        if(StringUtils.isNotEmpty(surfaceProcess.getStr2()))
            squence.add(surfaceProcess.getStr2());
        if(StringUtils.isNotEmpty(surfaceProcess.getStr3()))
            squence.add(surfaceProcess.getStr3());
        squence.add("打包");
        return squence;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("code", code).
                append("nextRecordNum", nextRecordNum).
                append("material", material).
                append("client", client).
                append("kaidan", kaidan).
                append("scheme", scheme).
                append("pack", pack).
                append("city", city).
                append("address", address).
                append("oxideFilm", oxideFilm).
                append("surfaceProcess", surfaceProcess).
                append("hardness", hardness).
                append("doEletr", doEletr).
                append("path", path).
                append("kpm", kpm).
                append("alPrice", alPrice).
                append("processingFee", processingFee).
                append("m_price", m_price).
                append("orderDate", orderDate).
                append("registerDate", registerDate).
                append("deadline", deadline).
                append("factSendDate", factSendDate).
                append("preSendDate", preSendDate).
                append("compStatus", compStatus).
                append("totalWeight", totalWeight).
                append("compWeight", compWeight).
                append("compQuantity", compQuantity).
                append("mcomment", mcomment).
                append("shixiao", shixiao).
                append("includeTax", includeTax).
                append("bill", bill).
                append("payWay", payWay).
                append("pickWay", pickWay).
                append("currentState", currentState).
                append("enable", enable).
                append("version", version).
                toString();
    }
}
