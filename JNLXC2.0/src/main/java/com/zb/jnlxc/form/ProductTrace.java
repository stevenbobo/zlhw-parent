package com.zb.jnlxc.form;

import com.zb.jnlxc.model.DataDict;
import com.zb.jnlxc.model.OrderDetail;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-1-26
 * Time: 上午12:13
 * To change this template use File | Settings | File Templates.
 */
public class ProductTrace {
    private Integer dbId;// 数值形主键
    private String setSize; //规定尺寸
    private String alHeatNum; //铝棒
    private DataDict pack ;// 包装方式
    private DataDict surfaceProcess ;// 表面处理表
    private String oxideFilm ;// 氧化膜
    private String aginghardness;// 时效实测硬度
    private Date preSendDate;// 预计发货日期
    private Integer uncompleteWeight ;// 待完成重量
    private Integer uncompleteQuantity;// 待完成重量
    private Integer orderWeight ;// 待完成重量
    private Integer orderQuantity;// 待完成重量
    private Integer finishWeight=0 ;// 待完成重量
    private Integer finishQuantiy=0;// 待完成重量
    private Byte isEnough ;// 是否已经完成 {id:0,text:'不足'}, {id:1, text: '足够'}, {id:2, text: '过量'}
    private OrderDetail orderDetail;//订单明细

    public String getSetSize() {
        return setSize;
    }

    public void setSetSize(String setSize) {
        this.setSize = setSize;
    }

    public String getAlHeatNum() {
        return alHeatNum;
    }

    public void setAlHeatNum(String alHeatNum) {
        this.alHeatNum = alHeatNum;
    }

    public DataDict getPack() {
        return pack;
    }

    public void setPack(DataDict pack) {
        this.pack = pack;
    }

    public DataDict getSurfaceProcess() {
        return surfaceProcess;
    }

    public void setSurfaceProcess(DataDict surfaceProcess) {
        this.surfaceProcess = surfaceProcess;
    }

    public String getOxideFilm() {
        return oxideFilm;
    }

    public void setOxideFilm(String oxideFilm) {
        this.oxideFilm = oxideFilm;
    }

    public String getAginghardness() {
        return aginghardness;
    }

    public void setAginghardness(String aginghardness) {
        this.aginghardness = aginghardness;
    }

    public Date getPreSendDate() {
        return preSendDate;
    }

    public void setPreSendDate(Date preSendDate) {
        this.preSendDate = preSendDate;
    }

    public Integer getUncompleteWeight() {
        return uncompleteWeight;
    }

    public void setUncompleteWeight(Integer uncompleteWeight) {
        this.uncompleteWeight = uncompleteWeight;
    }

    public Integer getUncompleteQuantity() {
        return uncompleteQuantity;
    }

    public void setUncompleteQuantity(Integer uncompleteQuantity) {
        this.uncompleteQuantity = uncompleteQuantity;
    }

    public Byte getEnough() {
        return isEnough;
    }

    public void setEnough(Byte enough) {
        isEnough = enough;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public Integer getFinishWeight() {
        return finishWeight;
    }

    public void setFinishWeight(Integer finishWeight) {
        this.finishWeight = finishWeight;
    }

    public Integer getFinishQuantiy() {
        return finishQuantiy;
    }

    public void setFinishQuantiy(Integer finishQuantiy) {
        this.finishQuantiy = finishQuantiy;
    }

    public Integer getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(Integer orderWeight) {
        this.orderWeight = orderWeight;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
