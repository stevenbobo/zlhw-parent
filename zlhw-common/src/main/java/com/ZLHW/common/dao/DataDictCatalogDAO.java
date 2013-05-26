package com.ZLHW.common.dao;

import com.ZLHW.base.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.common.model.DataDictCatalog;
@Repository
public class DataDictCatalogDAO extends DAO<DataDictCatalog,String> {
	private static final Log log = LogFactory.getLog(DataDictCatalogDAO.class);

}
