package com.zb.jnlxc.service;

import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.model.*;
import com.zb.jnlxc.model.Mould.MODEL_STATUS;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-16
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class MouldFlowService extends BaseService<MouldDAO,Mould, Integer> {
    private static final Logger log = LoggerFactory.getLogger(MouldFlowService.class);

    @Resource
    private SchemeDAO schemeDao;
    @Resource
    private FlowService flowService;
    @Resource
    private PaiChanMouldDAO paiChanMouldDAO;
    @Resource
    private MouldFixTaskDAO mouldFixTaskDAO;
    @Resource
    private MouldDanHuaTaskDAO mouldDanHuaTaskDAO;
    @Resource
    private MouldPaoPaiTaskDAO mouldPaoPaiTaskDAO;
    @Resource
    private MouldBaoFeiTaskDAO mouldBaoFeiTaskDAO;
    @Resource
    private PaichanMouldOrderDetailDAO paichanMouldOrderDetailDAO;
    @Resource
    private LiaoKuangDAO liaoKuangDAO;
    @Resource
    private NotifyFlowService notifyFlowService;
    @Resource
    DataDictService dataDictService;
    
    /**
     * 获取转修任务
     * @return
     */
    public Page loadMouldFixTask(Page page){
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from MouldFixTask t where 1=1 and isProcessed = false ");
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        mouldFixTaskDAO.findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

    /**
     * 获取拋排任务
     * @return
     */
    public Page loadMouldPaoPaiTask(Page page){
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from MouldPaoPaiTask t where 1=1 and isProcessed = false ");
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        mouldFixTaskDAO.findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

    /**
     * 获取氮化任务
     * @return
     */
    public Page loadMouldDanHuaTask(Page page){
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from MouldDanHuaTask t where 1=1 and isProcessed = false ");
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        mouldFixTaskDAO.findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

    /**
     * 异常模转修处理
     */
    public void ycmzxcl(Integer dbId,Integer syxc,Integer zjxc,Integer productTeamId,String nextStep,Integer supervisorDbId) {
        MouldFixTask mouldFixTask = mouldFixTaskDAO.getById(dbId);
        Mould mould = mouldFixTask.getMould();
        mould.setLastTask("异常模转修处理");
        if("氮化".equals(nextStep)){
            mould.setCurrentTask("氮化");
            mould.setProcessingScheme("");
            danHua(mould);
        }else if ("上机".equals(nextStep)){//抛排，上机
            mould.setCurrentTask("抛排收模处理");
            mould.setProcessingScheme(nextStep);
            paoPai(mould);
        }else if ("合格入库".equals(nextStep)){//抛排，上机
            mould.setCurrentTask("抛排收模处理");
            mould.setProcessingScheme(nextStep);
            paoPai(mould);
        }else if("报废".equals(nextStep)){
            mould.setProcessingScheme("");
            mould.setCurrentTask("报废退模处理");
            baoFei(mould);
        }
        update(mould);
        mouldFixTask.setIsProcessed(true);
        mouldFixTaskDAO.update(mouldFixTask);
    }

    /**
     * 抛排收模处理
     */
    private void ppsmcl(Integer dbId,String nextStep,Admin admin){
        MouldPaoPaiTask mouldPaoPaiTask = mouldPaoPaiTaskDAO.getById(dbId);
        Mould mould = mouldPaoPaiTask.getMould();
        mould.setLastTask("抛排收模处理");
        mould.setProcessingScheme("");
        if("氮化".equals(nextStep)){
            mould.setCurrentTask("氮化");
            danHua(mould);
        }else if ("修模".equals(nextStep)){//抛排，上机
            mould.setCurrentTask("异常模转修处理");
            fixMould(mould);
        }else if ("上机".equals(nextStep)){//抛排，上机
            mould.setCurrentTask("挤压班组收模");
            notifyShouMU(mould,admin);
        }else if ("合格入库".equals(nextStep)){//抛排，上机
            mould.setCurrentTask("抛排收模处理");
            notifyStorage(mould,admin);
        }
        update(mould);
        mouldPaoPaiTask.setIsProcessed(true);
        mouldPaoPaiTaskDAO.update(mouldPaoPaiTask);
    }
    
    //退模报废处理
    private void tmbfcl(Integer dbId,String nextStep,Admin admin){
        MouldBaoFeiTask mouldBaoFeiTask = mouldBaoFeiTaskDAO.getById(dbId);
        Mould mould = mouldBaoFeiTask.getMould();
        if("报废".equals(nextStep)){
            mould.setStatus(Mould.MODEL_STATUS.彻底报废.getValue());
        }else if("退模".equals(nextStep)){
            mould.setStatus(Mould.MODEL_STATUS.已退模.getValue());
            //todo 提醒添加模具
        }else if("返修".equals(nextStep)){
            mould.setStatus(Mould.MODEL_STATUS.定制.getValue());
            startmouldFlowByKey(mould);
        }
        update(mould);
        mouldBaoFeiTask.setIsProcessed(true);
        mouldBaoFeiTaskDAO.update(mouldBaoFeiTask);
    }
    
    /**
     * 用于前台获取的模具信息
     * @param taskId
     * @return
     */
    public Mould getMouldInfo(String taskId){
        Integer mouldId=(Integer)flowService.getContentMap(taskId, "mouldId");
        return this.getById(mouldId);
    }
    
    /**
     * 挤压班组收模
     */
    public void jybzsm(String taskId, Admin user,Map maps,String nextStep) {
        Mould mould = getMouldInfo(taskId);
        flowService.completeTask(taskId, nextStep, user);
        mould.setStatus(MODEL_STATUS.已收模.getValue());
    }
    
    /**
     * 挤压试模具使用
     */
    public void jysmsy(Integer mouldId, Admin user,Map map,List<PaichanMouldOrderDetail> paichanMouldOrderDetails){
        Mould mould = getById(mouldId);
        String liaoKuangCode = String.valueOf(map.get("liaoKuangCode"));
        //修改料框状态
        LiaoKuang liaoKuang = liaoKuangDAO.getBycode(liaoKuangCode);
        if(liaoKuang==null){
            liaoKuang = new LiaoKuang();
            liaoKuang.setCode(liaoKuangCode);
            liaoKuangDAO.create(liaoKuang);
        }
        liaoKuang.setUsing(true);
        liaoKuangDAO.update(liaoKuang);
        
        //保存模具生产记录
        for(PaichanMouldOrderDetail paichanMouldOrderDetail:paichanMouldOrderDetails){
        	paichanMouldOrderDetail.setLiaoKuang(liaoKuang);
            paichanMouldOrderDetail.setMould();
        	paichanMouldOrderDetailDAO.create(paichanMouldOrderDetail);
        }
        
        //获取卸模原因
        String xmyy = (String)map.get("xmyy");
        mould.setXmyy(xmyy);
        if("不卸模".equals(xmyy)){
        	log.info("不卸模，继续试模具，模具号:{}",mould.getDbId());
        }else{
        	log.info("卸模,模具号:{}",mould.getDbId());
        	mould.setStatus(MODEL_STATUS.抛排收模处理.getValue());
        	notifyPaoMu(mould,user);
        }
        update(mould);
    }
    
    /**
     * 机台选择模具试模
     */
    public void selectMouldToShiMo(Integer paiChanMouldId){
        PaiChanMould paoChanMould = paiChanMouldDAO.getById(paiChanMouldId);
        Mould mould = paoChanMould.getMould();
        String id = mould.getCode()+"_"+mould.getMouldRecordCount();
        ProcessInstance processInstance = flowService.getExecutionService().createProcessInstanceQuery().processInstanceKey(id).uniqueResult();
        flowService.getExecutionService().signalExecutionById(processInstance.getId());
        mould.setStatus(MODEL_STATUS.试模.getValue());
        update(mould);
    }
    

    /**
     * 通知收模
     * @param mould
     */
    private void notifyShouMU(Mould mould,Admin admin){
    	SimpleDateFormat dateformat1=new SimpleDateFormat("yyyyMMddHHmmss");
        int currentCount = mould.getMouldRecordCount();
        mould.setMouldRecordCount(currentCount+1);
        String id = mould.getCode()+"_"+mould.getMouldRecordCount();
        String notifyId = id+"-"+dateformat1.format(new Date());
        notifyFlowService.startNotify(notifyId, admin.getAccount(), "", "挤压班组收模", "/content/flow/newmould/jybzsm.vm" , "挤压班组收模");
        update(mould);
    }
    
    /**
     * 通知泡模收模确认
     * @param mould
     */
    private void notifyPaoMu(Mould mould,Admin admin){
    	SimpleDateFormat dateformat1=new SimpleDateFormat("yyyyMMddHHmmss");
        int currentCount = mould.getMouldRecordCount();
        mould.setMouldRecordCount(currentCount+1);
        String id = mould.getCode()+"_"+mould.getMouldRecordCount();
        String notifyId = id+"-"+dateformat1.format(new Date());
        notifyFlowService.startNotify(notifyId, admin.getAccount(), "", "泡模收模确认", "/content/flow/newmould/pmsmqr.vm" , "泡模收模确认");
        update(mould);
    }
    
    /**
     * 通知报废退模
     * @param mould
     */
    private void notifyTuiMu(Mould mould,Admin admin){
    	SimpleDateFormat dateformat1=new SimpleDateFormat("yyyyMMddHHmmss");
        int currentCount = mould.getMouldRecordCount();
        mould.setMouldRecordCount(currentCount+1);
        mould.setStatus(MODEL_STATUS.报废处理.getValue());
        String id = mould.getCode()+"_"+mould.getMouldRecordCount();
        String notifyId = id+"-"+dateformat1.format(new Date());
        notifyFlowService.startNotify(notifyId, admin.getAccount(), "", "报废退模处理", "/content/flow/newmould/bftmcl.vm" , "报废退模处理");
        update(mould);
    }

    /**
     * 模具入库提醒
     * @param mould
     */
    private void notifyStorage(Mould mould,Admin admin){
        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyyMMdd-HHmmss");
        String notifyId = mould.getCode()+"_"+dateformat1.format(new Date());
        Map map = new HashMap();
        map.put("mouldId", mould.getDbId());
        notifyFlowService.startNotify(notifyId, admin.getAccount(), "", "合格入库", "/content/flow/newmould/hgrk.vm" , "合格入库");
    }
    
    /**
     * 模具入库
     */
    public void mjrk(String taskId, Admin user,Map map){
    	Mould mould = getMouldInfo(taskId);
    	mould.setStatus(MODEL_STATUS.合格入库.getValue());
    	update(mould);
    	flowService.completeTask(taskId, user);
    }
    
    public void pmsmqr(String taskId, Admin user,Map map){
    	Mould mould = getMouldInfo(taskId);
    	update(mould);
    	flowService.completeTask(taskId, user);
    	paoPai(mould);
    }
    

    /**
     * 开启模具订单流程
     * @return
     */
    public ProcessInstance startmouldFlowByKey(Mould mould){
        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyyMMdd-HHmmss");
        String id = mould.getCode()+"_"+dateformat1.format(new Date());
        Map map = new HashMap();
        map.put("mouldId", mould.getDbId());
        logger.info("开启模具流程.id={}:",id);
        return flowService.startProcessInstanceByKey("newMould",id,map);
    }

    /**
     * 转修处理
     * @param mould
     */
    private void fixMould(Mould mould){
    	mould.setStatus(MODEL_STATUS.转修处理.getValue());
    	update(mould);
        MouldFixTask mouldFixTask = new MouldFixTask();
        mouldFixTask.setSource(mould.getLastTask());
        mouldFixTask.setMould(mould);
        mouldFixTask.setCreateDate(new Date());
        mouldFixTask.setIsProcessed(false);
        mouldFixTaskDAO.create(mouldFixTask);
    }

    /**
     * 抛排处理
     */
    private void paoPai(Mould mould){
    	mould.setStatus(MODEL_STATUS.抛排收模处理.getValue());
    	update(mould);
        MouldPaoPaiTask mouldPaoPaiTask = new MouldPaoPaiTask();
        mouldPaoPaiTask.setSource(mould.getLastTask());
        mouldPaoPaiTask.setMould(mould);
        mouldPaoPaiTask.setCreateDate(new Date());
        mouldPaoPaiTask.setIsProcessed(false);
        mouldPaoPaiTaskDAO.create(mouldPaoPaiTask);
    }

    /**
     * 氮化处理
     * @param mould
     */
    private void danHua(Mould mould){
    	mould.setStatus(MODEL_STATUS.氮化.getValue());
    	update(mould);
        MouldDanHuaTask mouldDanHuaTask = new MouldDanHuaTask();
        mouldDanHuaTask.setSource(mould.getLastTask());
        mouldDanHuaTask.setMould(mould);
        mouldDanHuaTask.setCreateDate(new Date());
        mouldDanHuaTask.setIsProcessed(false);
        mouldDanHuaTaskDAO.create(mouldDanHuaTask);
    }

    /**
     * 报废处理
     * @param mould
     */
    private void baoFei(Mould mould){
    	mould.setStatus(MODEL_STATUS.报废处理.getValue());
    	update(mould);
        MouldBaoFeiTask mouldBaoFeiTask = new MouldBaoFeiTask();
        mouldBaoFeiTask.setSource(mould.getLastTask());
        mouldBaoFeiTask.setMould(mould);
        mouldBaoFeiTask.setCreateDate(new Date());
        mouldBaoFeiTask.setIsProcessed(false);
        mouldBaoFeiTaskDAO.create(mouldBaoFeiTask);
    }
    
    /**
     * 模具入场验收
     */
    public void mjrcys(String taskId, Admin user,Map maps,String nextStep) {
    	Mould mould = getMouldInfo(taskId);
    	if("报废退模".equals(nextStep))
    		baoFei(mould);
    	flowService.completeTask(taskId,nextStep, user);
    }
    
    /**
     * 模具入场责任人分配
     */
    public void mjrczrrfp(String taskId, Admin user,Map maps,String nextStep) {
    	Mould mould = getMouldInfo(taskId);
    	fixMould(mould);
    	flowService.completeTask(taskId,nextStep, user);
    }
}
