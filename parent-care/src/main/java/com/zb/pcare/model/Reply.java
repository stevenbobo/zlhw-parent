package com.zb.pcare.model;

import com.ZLHW.base.table.IntTable;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-2
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
public class Reply extends IntTable {
    @ManyToOne
    private User user;
    @ManyToOne
    private Question question;
    private String reply;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建时间

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
