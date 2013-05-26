package com.ZLHW.achievement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ZLHW.base.HTable.HIntTable;
@Entity
@Table(name="app_AchievementInfo")
public class AchievementInfo extends HIntTable{
	@Column(length=50)
	private String name;
	@ManyToOne
	private AchievementRuleGroup ruleGroup;
	
	private int total;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AchievementRuleGroup getRuleGroup() {
		return ruleGroup;
	}

	public void setRuleGroup(AchievementRuleGroup ruleGroup) {
		this.ruleGroup = ruleGroup;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
}
