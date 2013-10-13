package com.zb.pcare.model;

import com.ZLHW.base.table.IntTable;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-2
 * Time: 下午11:57
 * To change this template use File | Settings | File Templates.
 */
public class Decision extends IntTable implements Serializable{
    /**触发规则
     * see http://www.cnblogs.com/vigarbuaa/archive/2013/01/03/2843427.html
    0 0 12 * * ?	每天12点触发
    0 15 10 ? * *	每天10点15分触发
    0 15 10 * * ?	每天10点15分触发
    0 15 10 * * ? *	每天10点15分触发
    0 15 10 * * ? 2005	2005年每天10点15分触发
    0 * 14 * * ?	每天下午的 2点到2点59分每分触发
    0 0/5 14 * * ?	每天下午的 2点到2点59分(整点开始，每隔5分触发)
    0 0/5 14,18 * * ?	每天下午的 2点到2点59分(整点开始，每隔5分触发)
    每天下午的 18点到18点59分(整点开始，每隔5分触发)
    0 0-5 14 * * ?	每天下午的 2点到2点05分每分触发
    0 10,44 14 ? 3 WED	3月分每周三下午的 2点10分和2点44分触发
    0 15 10 ? * MON-FRI	从周一到周五每天上午的10点15分触发
    0 15 10 15 * ?	每月15号上午10点15分触发
    0 15 10 L * ?	每月最后一天的10点15分触发
    0 15 10 ? * 6L	每月最后一周的星期五的10点15分触发
    0 15 10 ? * 6L 2002-2005	从2002年到2005年每月最后一周的星期五的10点15分触发
    0 15 10 ? * 6#3	每月的第三周的星期五开始触发
    0 0 12 1/5 * ?	每月的第一个中午开始每隔5天触发一次
    0 11 11 11 11 ?	每年的11月11号 11点11分触发(光棍节)
    */
    //触发条件
    private String triggerRule;
    //提前间隔时间
    private int remindInterval;
    //提前时间，小时为单位
    private int remindTimes;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate; //创建时间

    @ManyToOne
    private User user; //发起人

    private String remindContent;

    public String getTriggerRule() {
        return triggerRule;
    }

    public void setTriggerRule1(String triggerRule) {
        this.triggerRule = triggerRule;
    }

    public int getRemindInterval() {
        return remindInterval;
    }

    public void setRemindInterval(int remindInterval) {
        this.remindInterval = remindInterval;
    }

    public int getRemindTimes() {
        return remindTimes;
    }

    public void setRemindTimes(int remindTimes) {
        this.remindTimes = remindTimes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemindContent() {
        return remindContent;
    }

    public void setRemindContent(String remindContent) {
        this.remindContent = remindContent;
    }

    public void setTriggerRule(String triggerRule) {
        this.triggerRule = triggerRule;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("triggerRule", triggerRule)
                .append("remindInterval", remindInterval)
                .append("remindTimes", remindTimes)
                .append("createDate", createDate)
                .append("user", user)
                .append("remindContent", remindContent)
                .toString();
    }
}
