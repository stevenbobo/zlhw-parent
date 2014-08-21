package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
public class LiaoKuang extends HIntTable {
    @Column(length=30)
    private String code;//料框编号
    
    private Boolean using;//使用中

    private int jiya_weight;//挤压过磅重量

    private int shixiao_weight;//时效过磅重量

    private int yanghua_weight;//氧化过磅重量

    private int pengtu_weight;//喷涂过磅重量

    public int getJiya_weight() {
        return jiya_weight;
    }

    public void setJiya_weight(int jiya_weight) {
        this.jiya_weight = jiya_weight;
    }

    public int getShixiao_weight() {
        return shixiao_weight;
    }

    public void setShixiao_weight(int shixiao_weight) {
        this.shixiao_weight = shixiao_weight;
    }

    public int getYanghua_weight() {
        return yanghua_weight;
    }

    public void setYanghua_weight(int yanghua_weight) {
        this.yanghua_weight = yanghua_weight;
    }

    public int getPengtu_weight() {
        return pengtu_weight;
    }

    public void setPengtu_weight(int pengtu_weight) {
        this.pengtu_weight = pengtu_weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getUsing() {
        return using;
    }

    public void setUsing(Boolean using) {
        this.using = using;
    }
    
    
}
