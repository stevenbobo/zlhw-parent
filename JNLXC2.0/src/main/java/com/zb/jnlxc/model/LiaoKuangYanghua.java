package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-16
 * Time: 上午11:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="JNLXC_LiaoKuang_Yanghua")
@TableDeclare(comment = "料框", tableName = "LiaoKuangYanghua")
public class LiaoKuangYanghua extends HIntTable {
    @ManyToOne
    private LiaoKuang liaoKuang;//料框
    @ManyToOne
    private Admin operator; //操作员
    @Column(length=20)
    private String thickness; //氧化膜厚度

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建日期

    public LiaoKuang getLiaoKuang() {
        return liaoKuang;
    }

    public void setLiaoKuang(LiaoKuang liaoKuang) {
        this.liaoKuang = liaoKuang;
    }

    public Admin getOperator() {
        return operator;
    }

    public void setOperator(Admin operator) {
        this.operator = operator;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
