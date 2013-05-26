package com.zb.jnlxc.service;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.DataDictCatalogDAO;
import com.zb.jnlxc.model.DataDictCatalog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DataDictCatalogService extends BaseService<DataDictCatalogDAO,DataDictCatalog, String> {
	private static final Log log = LogFactory.getLog(DataDictCatalogService.class);
	
	@Autowired
	private DataDictCatalogDAO dataDictCatalogDao;

}
