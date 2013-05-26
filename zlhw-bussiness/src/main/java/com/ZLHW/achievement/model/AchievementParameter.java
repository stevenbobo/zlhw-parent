package com.ZLHW.achievement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ZLHW.base.HTable.HIntTable;
@Entity
@Table(name="app_AchievementParameter")
public class AchievementParameter extends HIntTable{
	@ManyToOne
	private AchievementInfo achievementInfo;
	@Column(length=30)
	private String key;
	@Column(length=100)
	private String value;
	public AchievementInfo getAchievementInfo() {
		return achievementInfo;
	}
	public void setAchievementInfo(AchievementInfo achievementInfo) {
		this.achievementInfo = achievementInfo;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
