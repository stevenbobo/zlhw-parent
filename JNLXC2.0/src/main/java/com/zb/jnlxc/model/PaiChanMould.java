package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhengbo.zb on 14-2-25.
 */
@Entity
@Table(name="JNLXC_PaiChan_mould")
@TableDeclare(comment = "排产记录表", tableName = "PaiChanMould")
public class PaiChanMould extends HIntTable {
    @ManyToOne
    private PaiChanRecord paiChanRecord;
    @ManyToOne
    private Mould mould;

    private boolean hasJiYa ;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建日期


    public boolean getHasJiYa() {
        return hasJiYa;
    }

    public void setHasJiYa(boolean hasJiYa) {
        this.hasJiYa = hasJiYa;
    }

    public PaiChanRecord getPaiChanRecord() {
        return paiChanRecord;
    }

    public void setPaiChanRecord(PaiChanRecord paiChanRecord) {
        this.paiChanRecord = paiChanRecord;
    }

    public Mould getMould() {
        return mould;
    }

    public void setMould(Mould mould) {
        this.mould = mould;
    }

    public boolean isHasJiYa() {
        return hasJiYa;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
