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
@TableDeclare(comment = "�Ų���¼��", tableName = "PaichanMouldOrderDetail")
public class PaichanMouldOrderDetail extends HIntTable {
    @ManyToOne
    private LiaoKuang liaoKuang; //��ǰ���ڵ��Ͽ�
    @ManyToOne
    private Mould mould;//�������õ�ģ��
    @ManyToOne
    private PaichanOrderDetail paichanOrderDetail; //������ϸ

    private Integer orderTotalMeter; //���������ף�
    private Integer compQuantity; //�������
    private Integer compWeight; //���������ǧ�ˣ�
    @Column(length=250)
    private String remarks; //��ע

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //��������

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
}
