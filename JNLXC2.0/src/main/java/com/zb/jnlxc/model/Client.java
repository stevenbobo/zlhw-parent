package com.zb.jnlxc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_Client")
@TableDeclare(comment = "客户表", tableName = "Client")
@Optimistic
public class Client extends HIntTable{
	@Column(length=30)
	private String clientCode; //客户编号 00010327前4位是业务员编号，后四位是该业务员的第n个客户
	private Integer nextOrderFormNum;//客户的下一个订单编号
	private String name; //名称
	@Column(length=50)
	private String clientPhone; //客户电话
	@Column(length=50)
	private String clientFax; //客户传真
	@Column(length=100)
	private String clientAddr; //客户地址
	@ManyToOne
	private City city; //城市类别
	@Column(length=30)
	private String clientPc; //客户邮编
	@ManyToOne
	private Admin agent; //业务员
	@ManyToOne
	private DataDict productType; //产品类型表
	private Byte saleType; //销售类型1:出口2:内销
	@Column(length=30)
	private String asset; //固定资产评估
    private byte credit; //信誉评级(0-10)
    @Column(length=60)
    private String taxNum; //税号
    @Column(length=60)
    private String bankName; //开户银行
    @Column(length=1024)
	private String remarks; //备注
	@Version  
    private Integer version;

    public byte getCredit() {
        return credit;
    }

    public void setCredit(byte credit) {
        this.credit = credit;
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getClientCode(){
	    return this.clientCode;
	}
	public void setClientCode(String clientCode){
	    this.clientCode = clientCode;
	}
	public String getName(){
	    return this.name;
	}
	public void setName(String name){
	    this.name = name;
	}
	public String getClientPhone(){
	    return this.clientPhone;
	}
	public void setClientPhone(String clientPhone){
	    this.clientPhone = clientPhone;
	}
	public String getClientFax(){
	    return this.clientFax;
	}
	public void setClientFax(String clientFax){
	    this.clientFax = clientFax;
	}
	public String getClientAddr(){
	    return this.clientAddr;
	}
	public void setClientAddr(String clientAddr){
	    this.clientAddr = clientAddr;
	}
	public City getCity(){
	    return this.city;
	}
	public void setCity(City city){
	    this.city = city;
	}
	public String getClientPc(){
	    return this.clientPc;
	}
	public void setClientPc(String clientPc){
	    this.clientPc = clientPc;
	}
	public Admin getAgent(){
	    return this.agent;
	}
	public void setAgent(Admin agent){
	    this.agent = agent;
	}
	public DataDict getProductType(){
	    return this.productType;
	}
	public void setProductType(DataDict productType){
	    this.productType = productType;
	}
	public Byte getSaleType(){
	    return this.saleType;
	}
	public void setSaleType(Byte saleType){
	    this.saleType = saleType;
	}
	public String getAsset(){
	    return this.asset;
	}
	public void setAsset(String asset){
	    this.asset = asset;
	}
	public String getRemarks(){
	    return this.remarks;
	}
	public void setRemarks(String remarks){
	    this.remarks = remarks;
	}
	public Integer getNextOrderFormNum() {
		return nextOrderFormNum;
	}
	public void setNextOrderFormNum(Integer nextOrderFormNum) {
		this.nextOrderFormNum = nextOrderFormNum;
	}


}
