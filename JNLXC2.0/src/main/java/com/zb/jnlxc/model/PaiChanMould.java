package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.BasicModel;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by zhengbo.zb on 14-2-25.
 */
@Entity
@Table(name="JNLXC_PaiChan_mould")
@TableDeclare(comment = "排产记录表", tableName = "PaiChanMould")
public class PaiChanMould extends HIntTable {
    @ManyToOne
    PaiChanRecord paiChanRecord;
    @ManyToOne
    Mould mould;
    boolean hasJiYa ;


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

    @Override
    public String toString() {
        return "PaiChanMould{" +
                "paiChanRecord=" + paiChanRecord +
                ", mould=" + mould +
                ", hasJiYa=" + hasJiYa +
                '}';
    }
}
