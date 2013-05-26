package com.zb.jnlxc.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.DeliveryRecord;

@Component
public class DeliveryRecordDAO extends DAO<DeliveryRecord,Integer> {
	private static final Log log = LogFactory.getLog(DeliveryRecordDAO.class);

	public DeliveryRecordDAO() {
		super();
	}

}
