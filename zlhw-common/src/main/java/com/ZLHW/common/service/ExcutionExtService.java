package com.ZLHW.common.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.ExcutionExtDAO;
import com.ZLHW.common.model.ExcutionExt;
@Transactional
@Service
public class ExcutionExtService extends BaseService<ExcutionExtDAO,ExcutionExt, String> {
	private static final Log log = LogFactory.getLog(ExcutionExtService.class);

}
