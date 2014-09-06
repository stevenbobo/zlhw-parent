package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhengbo.zb on 2014/8/25.
 */
@Entity
@Table(name="JNLXC_PaichanMouldOrderDetail")
@TableDeclare(comment = "生产记录", tableName = "ProductDetail")
public class ProductDetail extends HIntTable {
    @ManyToOne
    private LiaoKuang liaoKuang; //当前所在的料框
    @ManyToOne
    private Mould mould;//制造所用的模具
    @ManyToOne
    private PaichanOrderDetail paichanOrderDetail; //订单明细

    private Byte currentStep;//当前步骤

    private Integer orderTotalMeter; //总米数（米）
    private Integer compQuantity; //完成数量
    private Integer compWeight; //完成重量（千克）
    @Column(length=250)
    private String remarks; //备注

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建日期

    public LiaoKuang getLiaoKuang() {
        return liaoKuang;
    }

    public void setLiaoKuang(LiaoKuang liaoKuang) {
        this.liaoKuang = liaoKuang;
    }

    public Mould getMould() {
        return mould;
    }

    public void setMould(Mould mould) {
        this.mould = mould;
    }

    public PaichanOrderDetail getPaichanOrderDetail() {
        return paichanOrderDetail;
    }

    public void setPaichanOrderDetail(PaichanOrderDetail paichanOrderDetail) {
        this.paichanOrderDetail = paichanOrderDetail;
    }

    public Integer getOrderTotalMeter() {
        return orderTotalMeter;
    }

    public void setOrderTotalMeter(Integer orderTotalMeter) {
        this.orderTotalMeter = orderTotalMeter;
    }

    public Integer getCompQuantity() {
        return compQuantity;
    }

    public void setCompQuantity(Integer compQuantity) {
        this.compQuantity = compQuantity;
    }

    public Integer getCompWeight() {
        return compWeight;
    }

    public void setCompWeight(Integer compWeight) {
        this.compWeight = compWeight;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Byte currentStep) {
        this.currentStep = currentStep;
    }
}
