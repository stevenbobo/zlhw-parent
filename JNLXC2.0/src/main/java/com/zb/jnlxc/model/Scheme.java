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
@Table(name="JNLXC_Scheme")
@TableDeclare(comment = "图纸表", tableName = "Scheme")
@Optimistic
public class Scheme extends HIntTable{
	@Column(length=50)
	private String name; //图纸描述
	@Column(length=30,unique = true)
	private String code; //图纸编号
	private Integer nextMouldNum; //图纸的下一个模具号
	@ManyToOne
	private Admin designer; //设计人
	@ManyToOne
	private Admin uploader; //上传者
	@Temporal(TemporalType.TIMESTAMP)
	private Date designDate; //设计日期
	@ManyToOne
	private DataDict productType; //产品类型表
	@Column(length=100)
	private String dwgSrc; //dwg文件路径
	@Column(length=100)
	private String bmpSrc; //bmp文件路径
	private Byte currentState; //当前状态：0:正常1:流程中:
	private Byte enable; //0正常， 1 已删除
    private Byte payType; //0厂方负担1客户负担
    @Column(length=30)
    private String chargerName;  //厂方负责人
    @Column(length=30)
    private String mouldFee;    //客户负责费用
	@Version
    private Integer version;
	public String getName(){
	    return this.name;
	}
	public void setName(String name){
	    this.name = name;
	}
	public String getCode(){
	    return this.code;
	}
	public void setCode(String code){
	    this.code = code;
	}
	public Integer getNextMouldNum(){
	    return this.nextMouldNum;
	}
	public void setNextMouldNum(Integer nextMouldNum){
	    this.nextMouldNum = nextMouldNum;
	}
	public Admin getDesigner(){
	    return this.designer;
	}
	public void setDesigner(Admin designer){
	    this.designer = designer;
	}
	public Admin getUploader(){
	    return this.uploader;
	}
	public void setUploader(Admin uploader){
	    this.uploader = uploader;
	}
	public Date getDesignDate(){
	    return this.designDate;
	}
	public void setDesignDate(Date designDate){
	    this.designDate = designDate;
	}
	public DataDict getProductType(){
	    return this.productType;
	}
	public void setProductType(DataDict productType){
	    this.productType = productType;
	}
	public String getDwgSrc(){
	    return this.dwgSrc;
	}
	public void setDwgSrc(String dwgSrc){
	    this.dwgSrc = dwgSrc;
	}
	public String getBmpSrc(){
	    return this.bmpSrc;
	}
	public void setBmpSrc(String bmpSrc){
	    this.bmpSrc = bmpSrc;
	}
	public Byte getCurrentState(){
	    return this.currentState;
	}
	public void setCurrentState(Byte currentState){
	    this.currentState = currentState;
	}
	public Byte getEnable(){
	    return this.enable;
	}
	public void setEnable(Byte enable){
	    this.enable = enable;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getChargerName() {
        return chargerName;
    }

    public void setChargerName(String chargerName) {
        this.chargerName = chargerName;
    }

    public String getMouldFee() {
        return mouldFee;
    }

    public void setMouldFee(String mouldFee) {
        this.mouldFee = mouldFee;
    }
}
