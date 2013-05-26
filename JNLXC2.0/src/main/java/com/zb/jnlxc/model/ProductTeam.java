package com.zb.jnlxc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="JNLXC_ProductTeam")
@TableDeclare(comment = "机台", tableName = "ProductTeam")
public class ProductTeam extends HIntTable{
	@Column(length=50)
	private String name; //名称
	@ManyToOne
	private Depart depart;//所属车间
	
	private Integer charge_dbId; //白班负责人

	public String getName(){
	    return this.name;
	}
	public void setName(String name){
	    this.name = name;
	}

    public Integer getCharge_dbId() {
        return charge_dbId;
    }

    public void setCharge_dbId(Integer charge_dbId) {
        this.charge_dbId = charge_dbId;
    }

	public Depart getDepart() {
		return depart;
	}
	public void setDepart(Depart depart) {
		this.depart = depart;
	}
    
    
}
