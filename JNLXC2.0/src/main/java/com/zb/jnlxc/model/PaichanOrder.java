package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by zhengbo.zb on 14-2-25.
 */
@Entity
@Table(name="JNLXC_PaiChan_order")
@TableDeclare(comment = "排产记录表", tableName = "PaiChanOrder")
public class PaiChanOrder extends HIntTable {
    @ManyToOne
    PaiChanRecord paiChanRecord;
    @ManyToOne
    OrderForm orderForm;

    public PaiChanRecord getPaiChanRecord() {
        return paiChanRecord;
    }

    public void setPaiChanRecord(PaiChanRecord paiChanRecord) {
        this.paiChanRecord = paiChanRecord;
    }

    public OrderForm getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
    }
}
