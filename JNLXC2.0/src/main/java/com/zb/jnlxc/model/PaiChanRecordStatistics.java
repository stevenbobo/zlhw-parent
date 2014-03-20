package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhengbo.zb on 14-3-19.
 */
@Entity
@Table(name="JNLXC_PaiChanRecordStatistics")
@TableDeclare(comment = "排产统计表", tableName = "PaiChanRecordStatistics")
public class PaiChanRecordStatistics extends HIntTable {
    @Column(length=60)
    private String createDate; //添加日期
    private Integer nextPaiChanRecordNum;//下一个排产号，当天的第n次排产

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getNextPaiChanRecordNum() {
        return nextPaiChanRecordNum;
    }

    public void setNextPaiChanRecordNum(Integer nextPaiChanRecordNum) {
        this.nextPaiChanRecordNum = nextPaiChanRecordNum;
    }

    @Override
    public String toString() {
        return "PaiChanRecordStatistics{" +
                "createDate='" + createDate + '\'' +
                ", nextPaiChanRecordNum=" + nextPaiChanRecordNum +
                '}';
    }
}
