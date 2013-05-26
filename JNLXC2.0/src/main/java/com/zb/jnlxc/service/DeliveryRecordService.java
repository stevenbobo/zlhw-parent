package com.zb.jnlxc.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.DeliveryRecordDAO;
import com.zb.jnlxc.model.DeliveryRecord;
@Transactional
@Service
public class DeliveryRecordService extends BaseService<DeliveryRecordDAO,DeliveryRecord, Integer> {
	private static final Log log = LogFactory.getLog(DeliveryRecordService.class);

}
