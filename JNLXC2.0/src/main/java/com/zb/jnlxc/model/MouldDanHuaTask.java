package com.zb.jnlxc.model;

import javax.persistence.*;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import java.util.Date;

@Entity
@Table(name="JNLXC_MouldDanHuaTask")
@TableDeclare(comment = "模具氮化表", tableName = "MouldDanHuaTask")
public class MouldDanHuaTask extends HIntTable{
	@ManyToOne
    private Mould mould;

    private String source;//来源

    private Boolean isProcessed = false;//已经处理的

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建日期

	public Mould getMould() {
		return mould;
	}

	public void setMould(Mould mould) {
		this.mould = mould;
	}

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getProcessed() {
        return isProcessed;
    }

    public void setProcessed(Boolean processed) {
        isProcessed = processed;
    }
}
