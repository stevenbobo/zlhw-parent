package com.ZLHW.achievement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ZLHW.base.HTable.HIntTable;
@Entity
@Table(name="app_SinaUser")
public class SinaUser extends HIntTable{
	@Column(length=60)
	String sinaId;
	@Column(length=60)
	String sinaName;
	public String getSinaId() {
		return sinaId;
	}
	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}
	public String getSinaName() {
		return sinaName;
	}
	public void setSinaName(String sinaName) {
		this.sinaName = sinaName;
	}
	
}
