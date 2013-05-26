package com.zb.jnlxc.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.ProductTeam;

@Component
public class ProductTeamDAO extends DAO<ProductTeam,Integer> {
	private static final Log log = LogFactory.getLog(ProductTeamDAO.class);

	public ProductTeamDAO() {
		super();
	}

}
