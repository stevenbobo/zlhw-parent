package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhengbo.zb on 2014/6/1.
 */
@Entity
@Table(name="JNLXC_RemaindProduct")
@TableDeclare(comment = "机台", tableName = "RemaindProduct")
public class RemaindProduct extends HIntTable {
    @ManyToOne
    private PaichanOrderDetail paichanOrderDetail;
    private Integer remaindQuantity; //剩余数量
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建日期

    public PaichanOrderDetail getPaichanOrderDetail() {
        return paichanOrderDetail;
    }

    public void setPaichanOrderDetail(PaichanOrderDetail paichanOrderDetail) {
        this.paichanOrderDetail = paichanOrderDetail;
    }

    public Integer getRemaindQuantity() {
        return remaindQuantity;
    }

    public void setRemaindQuantity(Integer remaindQuantity) {
        this.remaindQuantity = remaindQuantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
