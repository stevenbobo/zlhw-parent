package com.zb.jnlxc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="JNLXC_RepairMouldRecord")
@TableDeclare(comment = "修模记录", tableName = "RepairMouldRecord")
public class RepairMouldRecord extends HIntTable{
	@Temporal(TemporalType.TIMESTAMP)
	private Date addDate; //添加日期
	@ManyToOne
	private Admin repairer; //修模工
	@ManyToOne
	private Mould mould; //模具表
	private Integer status; //修模状态0:成功1.失败
	@Version  
    private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getAddDate(){
	    return this.addDate;
	}
	public void setAddDate(Date addDate){
	    this.addDate = addDate;
	}
	public Admin getRepairer(){
	    return this.repairer;
	}
	public void setRepairer(Admin repairer){
	    this.repairer = repairer;
	}
	public Mould getMould(){
	    return this.mould;
	}
	public void setMould(Mould mould){
	    this.mould = mould;
	}
	public Integer getStatus(){
	    return this.status;
	}
	public void setStatus(Integer status){
	    this.status = status;
	}

}
