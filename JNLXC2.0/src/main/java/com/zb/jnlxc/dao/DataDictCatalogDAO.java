package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.DataDictCatalog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DataDictCatalogDAO extends DAO<DataDictCatalog,String> {
	private static final Log log = LogFactory.getLog(DataDictCatalogDAO.class);

}
