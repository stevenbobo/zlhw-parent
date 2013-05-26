package com.zb.jnlxc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="JNLXC_MouldRecord")
@TableDeclare(comment = "试模记录表", tableName = "MouldRecord")
@Optimistic
public class MouldRecord extends HIntTable{
	@ManyToOne
	private Mould mould; //模具表
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; //创建时间
    @Column(length=20)
	private String heatingTime; //加温时间
	@ManyToOne
	private ProductTeam productTeam; //机台号
	private String produceQuantity; //产量
	@Column(length=500)
	private String unloadingReason; //卸模原因
	@ManyToOne
	private DataDict material; //材质/铝棒规格
	@ManyToOne
	private Admin operator; //操作员
    @Column(length=100)
    private String lvgg;

    private Integer quantity;

	@Version  
    private Integer version;

    public String getLvgg() {
        return lvgg;
    }

    public void setLvgg(String lvgg) {
        this.lvgg = lvgg;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Mould getMould() {
		return mould;
	}
	public void setMould(Mould mould) {
		this.mould = mould;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public ProductTeam getProductTeam() {
		return productTeam;
	}
	public void setProductTeam(ProductTeam productTeam) {
		this.productTeam = productTeam;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getHeatingTime() {
		return heatingTime;
	}
	public void setHeatingTime(String heatingTime) {
		this.heatingTime = heatingTime;
	}

	public String getProduceQuantity() {
		return produceQuantity;
	}
	public void setProduceQuantity(String produceQuantity) {
		this.produceQuantity = produceQuantity;
	}
	
	public String getUnloadingReason() {
		return unloadingReason;
	}
	public void setUnloadingReason(String unloadingReason) {
		this.unloadingReason = unloadingReason;
	}
	public DataDict getMaterial() {
		return material;
	}
	public void setMaterial(DataDict material) {
		this.material = material;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Admin getOperator() {
		return operator;
	}
	public void setOperator(Admin operator) {
		this.operator = operator;
	}
}
