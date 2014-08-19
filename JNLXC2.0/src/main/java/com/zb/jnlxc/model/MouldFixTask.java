package com.zb.jnlxc.model;

import javax.persistence.*;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import java.util.Date;

@Entity
@Table(name="JNLXC_MouldFixTask")
@TableDeclare(comment = "模具转修表", tableName = "MouldFixTask")
public class MouldFixTask extends HIntTable{
	@ManyToOne
    private Mould mould;
    @Column(length=100)
	private String source;//来源

    private Boolean isProcessed = false;//已经处理的

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建日期

    private int syxc;//剩余限产

    private int zjxc;//增加限产
    @ManyToOne
    private ProductTeam productTeam;

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

    public Boolean getProcessed() {
        return isProcessed;
    }

    public void setProcessed(Boolean processed) {
        isProcessed = processed;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getSyxc() {
        return syxc;
    }

    public void setSyxc(int syxc) {
        this.syxc = syxc;
    }

    public int getZjxc() {
        return zjxc;
    }

    public void setZjxc(int zjxc) {
        this.zjxc = zjxc;
    }

    public ProductTeam getProductTeam() {
        return productTeam;
    }

    public void setProductTeam(ProductTeam productTeam) {
        this.productTeam = productTeam;
    }
}
