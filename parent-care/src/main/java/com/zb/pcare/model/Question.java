package com.zb.pcare.model;

import com.ZLHW.base.table.IntTable;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-2
 * Time: 下午10:55
 * To change this template use File | Settings | File Templates.
 */
public class Question extends IntTable {
    private String name;
    //DATE,YES/NO,TEXT,NUMBER
    private String replyType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建时间
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
