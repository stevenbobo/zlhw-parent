package com.ZLHW.common.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.DataDictCatalogDAO;
import com.ZLHW.common.model.DataDictCatalog;
@Transactional
@Service
public class DataDictCatalogService extends BaseService<DataDictCatalogDAO,DataDictCatalog, String> {
	private static final Log log = LogFactory.getLog(DataDictCatalogService.class);
	
	@Autowired
	private DataDictCatalogDAO dataDictCatalogDao;

}
