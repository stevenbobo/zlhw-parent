package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Steven.Zheng
 * Date: 13-2-14
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="JNLXC_MouldSize")
@TableDeclare(comment = "模具规格表", tableName = "MouldSize")
public class MouldSize extends HIntTable {

    @Column(length=50)
    private String size; //规格（111X111这种格式）
    private Integer price; //模具定价(元)
    private Integer pmprice; //配模价(元)

    @Column(length=50)
    private String bcdw; //包产吨位

    @ManyToOne
    private DataDict mouldType; //模具类型

    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime; //制造日期

    public DataDict getMouldType() {
        return mouldType;
    }

    public void setMouldType(DataDict mouldType) {
        this.mouldType = mouldType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPmprice() {
        return pmprice;
    }

    public void setPmprice(Integer pmprice) {
        this.pmprice = pmprice;
    }


    public String getBcdw() {
        return bcdw;
    }

    public void setBcdw(String bcdw) {
        this.bcdw = bcdw;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

}
