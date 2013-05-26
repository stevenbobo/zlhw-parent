package com.ZLHW.common.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.DataDictCatalogDAO;
import com.ZLHW.common.dao.DataDictDAO;
import com.ZLHW.common.model.DataDict;
import com.ZLHW.common.model.DataDictCatalog;
@Transactional
@Service
public class DataDictService extends BaseService<DataDictDAO,DataDict, Integer> {
	private static final Log log = LogFactory.getLog(DataDictService.class);
	
	@Autowired
	private DataDictDAO dataDictDao;
	@Autowired
	private DataDictCatalogDAO dataDictCatalogDao;
	@Autowired
	private DataDictCatalogService dataDictCatalogService;
	/**
	 * 根据目录编号获取字典
	 * @param code
	 * @return
	 */
	public List<DataDict> loadDictList(String code){
		return this.findHql("from DataDict t where t.dataDictCatalog.code=?", code);
	}
	
	/**
	 * 根据目录编号获取字典
	 * @param code
	 * @return
	 */
	public List<DataDict> findAllByCode(String code){
		return this.findHql("from DataDict t where t.dataDictCatalog.code=?", code);
	}

}
