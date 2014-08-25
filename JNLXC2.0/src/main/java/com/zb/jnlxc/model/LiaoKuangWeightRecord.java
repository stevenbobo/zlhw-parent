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
@Table(name="JNLXC_LiaoKuang")
@TableDeclare(comment = "料框", tableName = "LiaoKuang")
public class LiaoKuangWeightRecord extends HIntTable {
    @ManyToOne
    private LiaoKuang liaoKuang;

    @ManyToOne
    private Admin operator; //操作员

    private Integer weight;

    private String remark;

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
