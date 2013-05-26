package com.ZLHW.common.dao;

import com.ZLHW.base.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.common.model.Role_AuthorDetail;
@Repository
public class Role_AuthorDetailDAO extends DAO<Role_AuthorDetail,Integer> {
	private static final Log log = LogFactory.getLog(Role_AuthorDetailDAO.class);

}
