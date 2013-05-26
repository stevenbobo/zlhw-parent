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
@Table(name="JNLXC_Mould")
@TableDeclare(comment = "模具表", tableName = "Mould")
@Optimistic
public class Mould extends HIntTable{
	@Column(length=30)
	private String code; //模具编号 8081136-2-2图纸号-第n个模具
    @ManyToOne
    private MouldSize mouldSize;
	@ManyToOne
	private AssociationCompany manufacture; //制造厂家
	@Temporal(TemporalType.TIMESTAMP)
	private Date manuDate; //制造日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline; //交货日期
	private Integer preProNum; //实际寿命
	@ManyToOne
	private Admin register; //登记人
	private Byte status; //模具    0:定制1.试模 2.返修,3.可配半键报废,4判定5,彻底报废6,氮化,7,待产,8生产
	public static enum MODEL_STATUS {  
		  定制(0), 试模(1), 返修(2), 可配半键报废(3),判定(4),彻底报废(5),氮化(6),待产(7),生产(8) ;
		  private byte value;
		  MODEL_STATUS(int statue){
			  value=(byte) statue;
		  }
		  public byte getValue(){
			  return value;
		  }
		}  
	private Integer delayDate; //延迟时间
	@ManyToOne
	private DataDict scrap; //报废表
	@Temporal(TemporalType.TIMESTAMP)
	private Date scrapDate; //报废日期
	@ManyToOne
	private Scheme scheme; //图纸表
	@Column(length=1024)
	private String mcomment; //备注
	@Column(length=2)
	private String payType; //负担方式0,1
	@ManyToOne
	private Admin feeCharge; //厂方负担负责人
	@Column(length=50)
	private String mouldFee; //客服负担费用
	private Byte currentState; //当前状态：0:正常1:流程中:
	private Byte enable; //0正常， 1 已删除
	private int mouldRecordCount;
	@Version  
    private Integer version;

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	* 获取模具编号 8081136-2-2图纸号-第n个模具
	*/
	public String getCode(){
	    return this.code;
	}
	/**
	* 设置模具编号 8081136-2-2图纸号-第n个模具
	* @param code
	*/
	public void setCode(String code){
	    this.code = code;
	}

    public MouldSize getMouldSize() {
        return mouldSize;
    }

    public void setMouldSize(MouldSize mouldSize) {
        this.mouldSize = mouldSize;
    }

	/**
	* 获取制造厂家
	*/
	public AssociationCompany getManufacture(){
	    return this.manufacture;
	}
	/**
	* 设置制造厂家
	* @param manufacture
	*/
	public void setManufacture(AssociationCompany manufacture){
	    this.manufacture = manufacture;
	}
	/**
	* 获取制造日期
	*/
	public Date getManuDate(){
	    return this.manuDate;
	}
	/**
	* 设置制造日期
	* @param manuDate
	*/
	public void setManuDate(Date manuDate){
	    this.manuDate = manuDate;
	}
	/**
	* 获取预计生产数量
	*/
	public Integer getPreProNum(){
	    return this.preProNum;
	}
	/**
	* 设置预计生产数量
	* @param preProNum
	*/
	public void setPreProNum(Integer preProNum){
	    this.preProNum = preProNum;
	}
	/**
	* 获取登记人
	*/
	public Admin getRegister(){
	    return this.register;
	}
	/**
	* 设置登记人
	* @param register
	*/
	public void setRegister(Admin register){
	    this.register = register;
	}
	/**
	* 获取模具    0:定制1.试模 2.返修,3.可配半键报废,4判定5,彻底报废6,氮化,7,待产,8生产
	*/
	public Byte getStatus(){
	    return this.status;
	}
	/**
	* 设置模具    0:定制1.试模 2.返修,3.可配半键报废,4判定5,彻底报废6,氮化,7,待产,8生产
	* @param status
	*/
	public void setStatus(Byte status){
	    this.status = status;
	}
	/**
	* 获取延迟时间
	*/
	public Integer getDelayDate(){
	    return this.delayDate;
	}
	/**
	* 设置延迟时间
	* @param delayDate
	*/
	public void setDelayDate(Integer delayDate){
	    this.delayDate = delayDate;
	}
	/**
	* 获取报废表
	*/
	public DataDict getScrap(){
	    return this.scrap;
	}
	/**
	* 设置报废表
	* @param scrap
	*/
	public void setScrap(DataDict scrap){
	    this.scrap = scrap;
	}
	/**
	* 获取报废日期
	*/
	public Date getScrapDate(){
	    return this.scrapDate;
	}
	/**
	* 设置报废日期
	* @param scrapDate
	*/
	public void setScrapDate(Date scrapDate){
	    this.scrapDate = scrapDate;
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
	* 获取负担方式0,1
	*/
	public String getPayType(){
	    return this.payType;
	}
	/**
	* 设置负担方式0,1
	* @param payType
	*/
	public void setPayType(String payType){
	    this.payType = payType;
	}
	/**
	* 获取厂方负担负责人
	*/
	public Admin getFeeCharge(){
	    return this.feeCharge;
	}
	/**
	* 设置厂方负担负责人
	* @param feeCharge
	*/
	public void setFeeCharge(Admin feeCharge){
	    this.feeCharge = feeCharge;
	}
	/**
	* 获取客服负担费用
	*/
	public String getMouldFee(){
	    return this.mouldFee;
	}
	/**
	* 设置客服负担费用
	* @param mouldFee
	*/
	public void setMouldFee(String mouldFee){
	    this.mouldFee = mouldFee;
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
	public int getMouldRecordCount() {
		return mouldRecordCount;
	}
	public void setMouldRecordCount(int mouldRecordCount) {
		this.mouldRecordCount = mouldRecordCount;
	}

}
