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
public class LiaoKuangShiXiao extends HIntTable {
    @ManyToOne
    private LiaoKuang liaoKuang;//料框
    @ManyToOne
    private Admin operator; //操作员
    @Column(length=100)
    private String aginghardness;//实测硬度
    @Temporal(TemporalType.TIMESTAMP)
    private Date comeInTime;//时效进炉时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date comeOutTime;//时效出炉时间
    @Column(length=100)
    private String holdingTemperature;//时效保温温度
    @Column(length=100)
    private String shixiaoluhao;//时效炉号

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

    public String getAginghardness() {
        return aginghardness;
    }

    public void setAginghardness(String aginghardness) {
        this.aginghardness = aginghardness;
    }

    public Date getComeInTime() {
        return comeInTime;
    }

    public void setComeInTime(Date comeInTime) {
        this.comeInTime = comeInTime;
    }

    public Date getComeOutTime() {
        return comeOutTime;
    }

    public void setComeOutTime(Date comeOutTime) {
        this.comeOutTime = comeOutTime;
    }

    public String getHoldingTemperature() {
        return holdingTemperature;
    }

    public void setHoldingTemperature(String holdingTemperature) {
        this.holdingTemperature = holdingTemperature;
    }

    public String getShixiaoluhao() {
        return shixiaoluhao;
    }

    public void setShixiaoluhao(String shixiaoluhao) {
        this.shixiaoluhao = shixiaoluhao;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
