package com.zb.jnlxc.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.Message;

@Component
public class MessageDAO extends DAO<Message,Integer> {
	private static final Log log = LogFactory.getLog(MessageDAO.class);

	public MessageDAO() {
		super();
	}

}
