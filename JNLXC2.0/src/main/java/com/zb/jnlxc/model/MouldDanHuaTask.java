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

    private Integer zjxc;//增加限产

    private String danHuaLuHao;//氮化炉号

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;//入炉时间

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishTime;//出炉时间

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

    public String getDanHuaLuHao() {
        return danHuaLuHao;
    }

    public void setDanHuaLuHao(String danHuaLuHao) {
        this.danHuaLuHao = danHuaLuHao;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Boolean getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public Integer getZjxc() {
        return zjxc;
    }

    public void setZjxc(Integer zjxc) {
        this.zjxc = zjxc;
    }
}
