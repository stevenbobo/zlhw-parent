package com.zb.jnlxc.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_MouldFixTask")
@TableDeclare(comment = "模具转修表", tableName = "MouldFixTask")
public class MouldFixTask extends HIntTable{
	@ManyToOne
    private Mould mould;
	
	private String source;//来源

	public Mould getMould() {
		return mould;
	}

	public void setMould(Mould mould) {
		this.mould = mould;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	

}
