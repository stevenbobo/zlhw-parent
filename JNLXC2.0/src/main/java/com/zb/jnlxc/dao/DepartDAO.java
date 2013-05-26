package com.zb.jnlxc.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.Depart;

@Component
public class DepartDAO extends DAO<Depart,Integer> {
	private static final Log log = LogFactory.getLog(DepartDAO.class);

	public DepartDAO() {
		super();
	}

}
