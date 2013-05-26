package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.DataDict;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DataDictDAO extends DAO<DataDict,Integer> {
	private static final Log log = LogFactory.getLog(DataDictDAO.class);

}
