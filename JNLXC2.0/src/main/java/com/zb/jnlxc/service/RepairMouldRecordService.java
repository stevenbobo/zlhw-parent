package com.zb.jnlxc.service;

import java.util.Date;

import javax.annotation.Resource;

import com.zb.jnlxc.model.Admin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.MouldDAO;
import com.zb.jnlxc.dao.RepairMouldRecordDAO;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.RepairMouldRecord;

@Transactional
@Service
public class RepairMouldRecordService extends
		BaseService<RepairMouldRecordDAO,RepairMouldRecord, Integer> {
	private static final Log log = LogFactory
			.getLog(RepairMouldRecordService.class);
	@Resource
	private MouldDAO mouldDao;
	@Resource
	private FlowService flowService;
	/**
	 * 完成修模
	 * 
	 * @param taskId
	 * @throws BaseErrorModel 
	 */
	public void completeRepair(String taskId, boolean success,Mould mould,Admin user) throws BaseErrorModel {
		
		mouldDao.update(mould);
		RepairMouldRecord record = new RepairMouldRecord();
		record.setRepairer(user);
		record.setAddDate(new Date(System.currentTimeMillis()));
		record.setMould(mould);
		if (success) {
			flowService.completeTask(taskId, "正常",user);
			record.setStatus(0);
		} else {
			flowService.completeTask(taskId, "失败",user);
			record.setStatus(1);
		}
	}

}
