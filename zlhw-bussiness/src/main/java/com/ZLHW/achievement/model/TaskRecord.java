package com.ZLHW.achievement.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ZLHW.base.HTable.HIntTable;
@Entity
@Table(name="app_TaskRecord")
public class TaskRecord extends HIntTable{
	@ManyToOne
	AtomicTask atomicTask;
	@Temporal(TemporalType.TIME)
	Date createTime;
}
