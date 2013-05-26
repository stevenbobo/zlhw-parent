package com.zb.jnlxc.dao;

import java.util.List;

import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
@Component
public class TaskHistoryDao extends DAO {
	public TaskHistoryDao(){
	}
	public List<HistoryTaskImpl> findAll(){
		return sessionFactory.getCurrentSession().createCriteria(HistoryTaskImpl.class).list();
	}
}
