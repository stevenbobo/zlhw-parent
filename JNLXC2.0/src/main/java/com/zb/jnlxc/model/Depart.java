package com.zb.jnlxc.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_Depart")
@TableDeclare(comment = "部门表", tableName = "Depart")
public class Depart extends HIntTable {
	@Column(length=50)
	private String name; //名称
	private Integer charge_dbId; //负责人

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
}
