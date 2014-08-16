package com.zb.jnlxc.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_MouldDanHuaTask")
@TableDeclare(comment = "模具氮化表", tableName = "MouldDanHuaTask")
public class MouldDanHuaTask extends HIntTable{
	@ManyToOne
    private Mould mould;

	public Mould getMould() {
		return mould;
	}

	public void setMould(Mould mould) {
		this.mould = mould;
	}
	
	
}
