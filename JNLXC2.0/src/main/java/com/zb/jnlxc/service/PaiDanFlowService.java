package com.zb.jnlxc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import com.zb.jnlxc.dao.AdminDAO;
import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zb.jnlxc.dao.MouldDAO;
import com.zb.jnlxc.dao.MouldRecordDAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.MouldRecord;
import com.zb.jnlxc.model.Mould.MODEL_STATUS;
@Transactional
@Service("PaiDanFlowService")
public class PaiDanFlowService {
	
	Logger logger = LoggerFactory.getLogger(PaiDanFlowService.class);
	@Resource
	private FlowService flowService;
	@Resource
	private MouldRecordDAO mouldRecordDAO;
	@Resource
	private MouldDAO mouldDAO;
    @Resource
    private AdminDAO adminDAO;
    @Resource
    DataDictService dataDictService;
	
	/**
	 * 开启模具订单流程
	 * @param id 模具订单号
	 * @return
	 */
	public ProcessInstance startmouldFlowByKey(String id,Map map){
		return flowService.startProcessInstanceByKey("paidan",id,map);
	}

    /**
     * 用于前台获取的模具信息
     * @param taskId
     * @return
     */
    public Mould getMouldInfo(String taskId){
        Integer mouldId=(Integer)flowService.getContentMap(taskId, "mouldId");
        return mouldDAO.getById(mouldId);
    }

	/**
	 * 开启模具订单流程
	 * @param id 模具订单号
	 * @return
	 */
	public void startPaiDanFlow(int id){
		Mould mould = mouldDAO.getById(id);
		int currentCount = mould.getMouldRecordCount();
		mould.setMouldRecordCount(currentCount+1);
		mouldDAO.saveOrupdate(mould);
		String flowId = mould.getCode()+"_"+currentCount;
		Map map = new HashMap();
		map.put("mouldId", id);
		logger.info("开启排单流程,id={}",flowId);
		startmouldFlowByKey(flowId,map);
	}
	
	/**
	 * 仓管分配
	 */
	public void distribution(String taskId,Integer moulderId,String remarks,Admin user) {
        Mould mould = getMouldInfo(taskId);
        mould.setMcomment(remarks);
        mould.setStatus(MODEL_STATUS.生产.getValue());
        mouldDAO.update(mould);
		Map map = new HashMap();
		map.put("moulder", adminDAO.getById(moulderId).getAccount());
		flowService.completeTask(taskId,map,user);

	}
	/**
	 * 模具工记录模具记录
	 */
	public void record(String taskId,MouldRecord record,String nextStep,String remarks,Admin user) {
        logger.info("模具工记录模具记录:{} ",user.getName());
        Mould mould = getMouldInfo(taskId);
        if(nextStep.equals("氮化")){
            mould.setStatus(MODEL_STATUS.氮化.getValue());
        }else if(nextStep.equals("模具主管")){
            mould.setStatus(MODEL_STATUS.判定.getValue());
        }else if(nextStep.equals("仓库入库")){
            mould.setStatus(MODEL_STATUS.待产.getValue());
        }
        mould.setMcomment(remarks);
        mouldDAO.update(mould);
        record.setCreateTime(new Date());
        record.setOperator(user);
        record.setMould(mould);
		mouldRecordDAO.create(record);
		flowService.completeTask(taskId,nextStep,user);
	}
	/**
	 * 氮化
	 */
	public void danhua(String taskId,String remarks,Admin user){
		logger.info("氮化员:{} ",user.getName());
        Mould mould = getMouldInfo(taskId);
        mould.setMcomment(remarks);
        mouldDAO.update(mould);
        mould.setMcomment(remarks);
        mouldDAO.update(mould);
		flowService.completeTask(taskId,user);
	}
	/**
	 * 模具主管判定
	 */
	public void panding(String taskId,String nextStep,String remarks,String scrapId,Admin user) {
		Mould m = getMouldInfo(taskId);
        m.setMcomment(remarks);
		if(nextStep.equals("彻底报废")){
            Integer scrapDbId = Integer.parseInt(scrapId);
            dataDictService.loadById(scrapDbId);
            m.setScrap(dataDictService.loadById(scrapDbId));
            m.setStatus(MODEL_STATUS.彻底报废.getValue());
        }else if(nextStep.equals("正常入库")){
            m.setStatus(MODEL_STATUS.待产.getValue());
        }
		mouldDAO.update(m);
		flowService.completeTask(taskId,nextStep,user);

	}
	/**
	 * 仓管入库
	 */
	public void storage(String taskId,String remarks,Admin user){
		Mould m = getMouldInfo(taskId);
        m.setMcomment(remarks);
		m.setStatus(MODEL_STATUS.待产.getValue());
		mouldDAO.update(m);
		flowService.completeTask(taskId,user);
	}
}
