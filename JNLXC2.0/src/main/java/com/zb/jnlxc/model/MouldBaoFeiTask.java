package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="JNLXC_MouldBaoFeiTask")
@TableDeclare(comment = "模具氮化表", tableName = "MouldBaoFeiTask")
public class MouldBaoFeiTask extends HIntTable{
	@ManyToOne
    private Mould mould;

    private String source;//来源

    private Boolean isProcessed = false;//已经处理的
    @Column(length=100)
    private String dealReason;//处理理由
    @Column(length=300)
    private String dealRemark;//处理备注

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

    public Boolean getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public String getDealReason() {
        return dealReason;
    }

    public void setDealReason(String dealReason) {
        this.dealReason = dealReason;
    }

    public String getDealRemark() {
        return dealRemark;
    }

    public void setDealRemark(String dealRemark) {
        this.dealRemark = dealRemark;
    }
}
