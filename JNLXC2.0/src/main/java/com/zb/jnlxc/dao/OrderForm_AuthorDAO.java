package com.zb.jnlxc.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.OrderForm_Author;
@Component
public class OrderForm_AuthorDAO extends DAO<OrderForm_Author,Integer> {
	private static final Log log = LogFactory.getLog(OrderForm_AuthorDAO.class);
	
	public OrderForm_AuthorDAO(){
		super();
	}

}
