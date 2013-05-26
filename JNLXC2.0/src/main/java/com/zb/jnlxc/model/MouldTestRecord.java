package com.zb.jnlxc.model;

import java.util.Date;

import javax.persistence.*;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="JNLXC_MouldTestRecord")
@TableDeclare(comment = "试模记录表", tableName = "MouldTestRecord")
@Optimistic
public class MouldTestRecord extends HIntTable{
	@ManyToOne
	private Mould mould; //模具表
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; //创建时间
	@ManyToOne
	private ProductTeam productTeam; //部门表
	private Integer useStickNum; //用棒数量
    @Column(length=100)
    private String lbgg;//铝棒规格
    @Column(length=200)
	private String conclusion; //试模结论
	@ManyToOne
	private Admin qualityTester; //质检工
	@ManyToOne
	private Admin repairman; //修模工
    @ManyToOne
    private Admin operater; //记录添加人
	@Version
    private Integer version;

    public String getLbgg() {
        return lbgg;
    }

    public void setLbgg(String lbgg) {
        this.lbgg = lbgg;
    }

    public Admin getOperater() {
        return operater;
    }

    public void setOperater(Admin operater) {
        this.operater = operater;
    }

    public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	* 获取模具表
	*/
	public Mould getMould(){
	    return this.mould;
	}
	/**
	* 设置模具表
	* @param mould
	*/
	public void setMould(Mould mould){
	    this.mould = mould;
	}
	/**
	* 获取创建时间
	*/
	public Date getCreateTime(){
	    return this.createTime;
	}
	/**
	* 设置创建时间
	* @param createTime
	*/
	public void setCreateTime(Date createTime){
	    this.createTime = createTime;
	}
	
	public ProductTeam getProductTeam() {
		return productTeam;
	}
	public void setProductTeam(ProductTeam productTeam) {
		this.productTeam = productTeam;
	}
	/**
	* 获取用棒数量
	*/
	public Integer getUseStickNum(){
	    return this.useStickNum;
	}
	/**
	* 设置用棒数量
	* @param useStickNum
	*/
	public void setUseStickNum(Integer useStickNum){
	    this.useStickNum = useStickNum;
	}
	/**
	* 获取试模结论
	*/
	public String getConclusion(){
	    return this.conclusion;
	}
	/**
	* 设置试模结论
	* @param conclusion
	*/
	public void setConclusion(String conclusion){
	    this.conclusion = conclusion;
	}
	/**
	* 获取质检工
	*/
	public Admin getQualityTester(){
	    return this.qualityTester;
	}
	/**
	* 设置质检工
	* @param qualityTester
	*/
	public void setQualityTester(Admin qualityTester){
	    this.qualityTester = qualityTester;
	}
	/**
	* 获取修模工
	*/
	public Admin getRepairman(){
	    return this.repairman;
	}
	/**
	* 设置修模工
	* @param repairman
	*/
	public void setRepairman(Admin repairman){
	    this.repairman = repairman;
	}


}
