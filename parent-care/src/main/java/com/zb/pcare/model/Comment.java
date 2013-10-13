package com.zb.pcare.model;

import com.ZLHW.base.table.IntTable;

import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-3
 * Time: 下午10:40
 * To change this template use File | Settings | File Templates.
 */
public class Comment extends IntTable {
    @ManyToOne
    private User user; //评论人
    @ManyToOne
    private Decision decision;
    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
