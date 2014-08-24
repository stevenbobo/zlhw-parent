package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-16
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="JNLXC_PaiChanMouldLiaoKuang")
@TableDeclare(comment = "模具排产-料框", tableName = "PaiChanMouldLiaoKuang")
public class PaiChanMouldLiaoKuang extends HIntTable {
	
    @ManyToOne
    PaichanMouldOrderDetail paichanMouldOrderDetail;
    
    @ManyToOne
    LiaoKuang liaoKuang;

    public PaichanMouldOrderDetail getPaichanMouldOrderDetail() {
		return paichanMouldOrderDetail;
	}

	public void setPaichanMouldOrderDetail(
			PaichanMouldOrderDetail paichanMouldOrderDetail) {
		this.paichanMouldOrderDetail = paichanMouldOrderDetail;
	}

	public LiaoKuang getLiaoKuang() {
        return liaoKuang;
    }

    public void setLiaoKuang(LiaoKuang liaoKuang) {
        this.liaoKuang = liaoKuang;
    }
}
