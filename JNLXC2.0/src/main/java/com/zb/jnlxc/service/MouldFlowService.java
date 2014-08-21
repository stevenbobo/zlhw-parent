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
     * 获取拋拍任务
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
     * 获取拋拍任务
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

    private void ppsmcl(Integer dbId,String nextStep){
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
            startMouldProduce(mould);
        }else if ("合格入库".equals(nextStep)){//抛排，上机
            mould.setCurrentTask("抛排收模处理");
            mouldStorage(mould);
        }
        mouldPaoPaiTask.setIsProcessed(true);
        mouldPaoPaiTaskDAO.update(mouldPaoPaiTask);
    }
    
    //退模报废处理
    private void tmbfcl(Integer dbId,String nextStep,Admin admin, Integer reciverId,Integer reciverGroupId){
        MouldBaoFeiTask mouldBaoFeiTask = mouldBaoFeiTaskDAO.getById(dbId);
        Mould mould = mouldBaoFeiTask.getMould();
        if("报废".equals(nextStep)){
            mould.setStatus(Mould.MODEL_STATUS.彻底报废.getValue());
        }else if("退模".equals(nextStep)){
            mould.setStatus(Mould.MODEL_STATUS.退模.getValue());
            notifyFlowService.startNotify(dbId+"",admin.getDbId(),reciverId,null,"addMould.vm","提醒补模添加模具(发单)");
        }else if("返修".equals(nextStep)){
            mould.setStatus(Mould.MODEL_STATUS.定制.getValue());
            startmouldFlowByKey(mould);
        }
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
    }
    
    /**
     * 挤压模具加温确认
     */
    public void jymjjwqr(String taskId, Admin user,Map maps,String nextStep) {
        Mould mould = getMouldInfo(taskId);
        mould.setStatus(MODEL_STATUS.待产.getValue());
        update(mould);
        flowService.completeTask(taskId, nextStep, user);
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
        mould.setStatus(MODEL_STATUS.生产.getValue());
        update(mould);
    }
    

    /**
     * 开启上机流程
     * @param mould
     */
    private void startMouldProduce(Mould mould){
        
        int currentCount = mould.getMouldRecordCount();
        mould.setMouldRecordCount(currentCount+1);
        String id = mould.getCode()+"_"+mould.getMouldRecordCount();
        Map map = new HashMap();
        map.put("mouldId", mould.getDbId());
        flowService.startProcessInstanceByKey("mouldProduce", id, map);
        update(mould);
    }

    /**
     * 模具入库
     * @param mould
     */
    private void mouldStorage(Mould mould){
        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyyMMdd-HHmmss");
        String id = mould.getCode()+"_"+dateformat1.format(new Date());
        Map map = new HashMap();
        map.put("mouldId", mould.getDbId());
        flowService.startProcessInstanceByKey("mould_storage", id, map);
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
        MouldBaoFeiTask mouldBaoFeiTask = new MouldBaoFeiTask();
        mouldBaoFeiTask.setSource(mould.getLastTask());
        mouldBaoFeiTask.setMould(mould);
        mouldBaoFeiTask.setCreateDate(new Date());
        mouldBaoFeiTask.setIsProcessed(false);
        mouldBaoFeiTaskDAO.create(mouldBaoFeiTask);
    }
}
