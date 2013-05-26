package com.ZLHW.achievement.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ZLHW.base.HTable.HIntTable;
@Entity
@Table(name="app_AchievementRecord")
public class AchievementRecord extends HIntTable{
	@ManyToOne
	AchievementInfo achievementInfo;
	@Temporal(TemporalType.TIME)
	Date createTime;
	@ManyToOne
	SinaUser user;
	public AchievementInfo getAchievementInfo() {
		return achievementInfo;
	}
	public void setAchievementInfo(AchievementInfo achievementInfo) {
		this.achievementInfo = achievementInfo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public SinaUser getUser() {
		return user;
	}
	public void setUser(SinaUser user) {
		this.user = user;
	}
	
	
	
}
