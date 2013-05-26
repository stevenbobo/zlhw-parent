package com.zb.jnlxc.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.DepartDAO;
import com.zb.jnlxc.model.Depart;
@Transactional
@Service
public class DepartService extends BaseService<DepartDAO,Depart, Integer> {
	private static final Log log = LogFactory.getLog(DepartService.class);

}
