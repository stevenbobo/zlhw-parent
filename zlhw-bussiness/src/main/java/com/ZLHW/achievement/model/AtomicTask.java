package com.ZLHW.achievement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ZLHW.base.HTable.HIntTable;
@Entity
@Table(name="app_AtomicTask")
public class AtomicTask extends HIntTable{
	@Column(length=60)
	private String name;
}
