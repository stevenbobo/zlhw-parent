package com.zb.jnlxc.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.AssociationCompany;

@Component
public class AssociationCompanyDAO extends DAO<AssociationCompany,Integer> {
	private static final Log log = LogFactory.getLog(AssociationCompanyDAO.class);

	public AssociationCompanyDAO() {
		super();
	}

}
