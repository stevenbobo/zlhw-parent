package com.zb.jnlxc.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.RepairMouldRecord;

@Component
public class RepairMouldRecordDAO extends DAO<RepairMouldRecord,Integer> {
	private static final Log log = LogFactory.getLog(RepairMouldRecordDAO.class);

	public RepairMouldRecordDAO() {
		super();
	}

}
