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
    @Column(length=50)
    private String currentStep;//挤压,氧化, 喷涂, 时效,打包;

    //步骤 例如：挤压氧化 挤压时效氧化
    private String steps;

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

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getNextStep(){
        int length = steps.length();
        int index = steps.indexOf(currentStep);
        if(index<=length-4){
            return steps.substring(index+2,index+4);
        }else {
            return null;
        }
    }

}
