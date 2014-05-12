package com.zb.jnlxc.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.ZLHW.base.dao.QueryCondition;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.model.*;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.model.Mould.MODEL_STATUS;

@Transactional
@Service
public class MouldService extends BaseService<MouldDAO,Mould, Integer>{
	private static final Logger log = LoggerFactory.getLogger(MouldService.class);
	@Resource
	private SchemeDAO schemeDao;
	@Resource
	private FlowService flowService;
	@Resource
	private MouldTestRecordDAO mouldTestRecordDAO;
    @Resource
    private MouldRecordDAO mouldRecordDAO;
    @Resource
    private PaiChanRecordDAO paiChanRecordDAO;
    @Resource
    private PaiChanMouldDAO paiChanMouldDAO;
    @Resource
    DataDictService dataDictService;
    @Resource
    private ProductRecordService productRecordService;
	/**
	 * 生成模具编号
	 */
	public String generateCode(Integer schemeId) {
		//图纸的下一个模具号
        Scheme scheme =  schemeDao.getById(schemeId);
		int modelNum = scheme.getNextMouldNum();
		return scheme.getName()+"-"+String.format("%03d",modelNum);
	}

	/**
	 * 保存模具，开启新的流程
	 * @param mould
	 * @return
	 * @throws BaseErrorModel
	 */
	public Mould saveMould(Mould mould) throws BaseErrorModel{
        this.getDao().refresh(mould.getManufacture());

		mould.setMouldRecordCount(0);
		mould.setCurrentState((byte)1);//修改为流程中状态
        mould.setStatus(MODEL_STATUS.定制.getValue());
        mould.setManuDate(new Date());
        this.create(mould);
		
		//更新图纸的下一个模具号
        schemeDao.refresh(mould.getScheme());
		Scheme scheme = mould.getScheme();
		scheme.setNextMouldNum(scheme.getNextMouldNum()+1);
		this.schemeDao.update(scheme);
        Map map = new HashMap();
		//将模具和外协添加到流程中
		map.put("mouldId", mould.getDbId());
		map.put("association", mould.getManufacture().getAgent().getAccount());
        //初始化试模次数
        map.put("smcs","0");
        map.put("isReturn",false);

        Map map2 = new HashMap(map);
        map2.put("mould",mould);
        updateCombination(mould.getDbId(), map2);

        this.startmouldFlowByKey(mould.getCode(), map);
		return mould;
	}
	/**
	 * 保存模具，完成流程任务
	 * @param mould 模具
	 * @param taskId 任务号
	 * @return
	 * @throws BaseErrorModel 
	 */
	public Mould saveMould(Mould mould,String taskId,Admin user) throws BaseErrorModel{
		if(flowService.getContentMap(taskId, "payWay")!=null){
			String payWay = flowService.getContentMap(taskId, "payWay").toString();
			mould.setPayType(payWay);
			if("厂方负担".equals(payWay))
				mould.setFeeCharge((Admin) flowService.getContentMap(taskId, "feeCharge"));
			else if("客户负担".equals(payWay))
				mould.setMouldFee((String) flowService.getContentMap(taskId, "mouldFee"));
		}
		this.saveMould(mould);
		flowService.completeTask(taskId,user);
		return mould;
	}
	
	/**
	 * 通过分页载入模具
	 * @param page
	 * @return
	 */
	public Page LoadMould(Page page){

        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from Mould t where 1=1 ");
        if(StringUtils.isNotEmpty(page.getParameter("key"))){
            String key = page.getParameter("key");
            hql.append("and ( t.code like :key or t.mouldSize.size like :key )");
            queryConditions.add(new QueryCondition("key", "%"+key+"%"));
        }
        if(StringUtils.isNotBlank(page.getParameter("paiChanRecordId"))){
            Integer paiChanRecordId = Integer.parseInt(page.getParameter("paiChanRecordId"));
            PaiChanRecord paiChanRecord = paiChanRecordDAO.getById(paiChanRecordId);
            String mouldList = paiChanRecord.getMouldIds();
            if(StringUtils.isNotBlank(mouldList))
                hql.append("and  t.dbId in (").append(mouldList).append(") ");
        }
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
	}


    public List<Mould> getUsedMouldListByScheme(Integer schemeId){
        Scheme scheme= schemeDao.loadById(schemeId);
        return this.getDao().getUsedMouldListByScheme(scheme);
    }
	/**
	 * 删除模具
	 * @param mouldId
	 * @throws BaseErrorModel
	 */
	public void deleteMould(Integer mouldId) throws BaseErrorModel{
        Mould mould = getById(mouldId);
        mouldRecordDAO.deleteByMould(mould);
        mouldTestRecordDAO.deleteByMould(mould);
        flowService.deleteProcessInstanceCascade("mouldFlow."+mould.getCode());
        this.getDao().delete(mould);
	}
	/**
	 * 更新模具
	 * @param mould
	 * @throws BaseErrorModel
	 */
	public Mould updateMould(Mould mould) throws BaseErrorModel{
        this.getDao().getSession().refresh(mould.getScheme());
		this.update(mould);
        return mould;
	}

	/**
	 * 报废模具
	 * @throws BaseErrorModel 
	 */
	public void scrapMould(Mould mould,Admin user) throws BaseErrorModel{
		Mould newMould=this.loadById(mould.getDbId());
		newMould.setScrap(mould.getScrap());
		newMould.setScrapDate(new Date());
		newMould.setRegister(user);
		newMould.setStatus(MODEL_STATUS.彻底报废.getValue());
		this.update(newMould);
		
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
	 * 外协购买模具完成，下一步是仓管修改为修模状态
	 * @param taskId
	 * @throws BaseErrorModel 
	 */
	public void buyMouldComplete(String taskId,String remark,Admin user) throws BaseErrorModel{
        Mould m=this.getMouldInfo(taskId);
		m.setMcomment(remark);
		this.update(m);
		flowService.completeTask(taskId,user);
	}
	
	//模具    0:正常状态1.修模状态2.报废状态,3.退模状态"
	/**
	 * 仓库管理员修改为修模状态
	 */
	public void changeToRepairMould(String taskId,Admin repair,String remark,Admin user) throws BaseErrorModel{
        Mould mould=this.getMouldInfo(taskId);
        mould.setMcomment(remark);
        mould.setStatus(MODEL_STATUS.定制.getValue());
		this.update(mould);
		Map map =new HashMap();
		map.put("repairer", repair.getAccount());//指定修模工
		map.put("warehouse", flowService.getTaskService().getTask(taskId).getAssignee());//指定本流程的仓管
		flowService.completeTask(taskId,"修模",map,user);
	}
	
	/**
	 * 模具主管修改为正常状态
	 * @param taskId
	 * @throws BaseErrorModel 
	 */
	public void changeToNormalMould(String taskId,String remark,Admin user) throws BaseErrorModel{
        Mould m=this.getMouldInfo(taskId);
		m.setMcomment(remark);
		m.setStatus(MODEL_STATUS.定制.getValue());
		this.update(m);
		flowService.completeTask(taskId,user);
	}
	/**
	 * 修改为退货状态
	 * @param taskId
	 * @throws BaseErrorModel 
	 */
	public void changeToReturn(String taskId,String remmark,Admin user) throws BaseErrorModel{
        Mould mould=this.getMouldInfo(taskId);
        mould.setMcomment(remmark);
        mould.setStatus(MODEL_STATUS.可配半键报废.getValue());
		this.update(mould);
		flowService.completeTask(taskId,user);
	}
	/**
	 * 修改为报废状态
	 * @param taskId
	 * @param scrapId
	 * @throws BaseErrorModel 
	 */
	public void changeToScrapped(String taskId,Integer scrapId,Admin user) throws BaseErrorModel{
		Mould mould=this.getMouldInfo(taskId);
		mould.setStatus(MODEL_STATUS.返修.getValue());
		mould.setScrap(dataDictService.getById(scrapId));
		this.update(mould);
		flowService.completeTask(taskId,user);
	}
	
	/**
	 * 模具主管查看
	 * @param taskId
	 * @param success
	 */
	public void moulderLook(String taskId,boolean success,Admin user){
		if(success){
			flowService.completeTask(taskId, "有库存",user);
		}
		else{
			flowService.completeTask(taskId,"无库存",user);
			logger.info("无库存通知业务员 taskId={}",taskId);
		}
	}
	/**
	 * 查找该图纸的所有可用模具
	 * @param schemeId
	 * @return
	 */
	public List<Mould> findAllNormalMouldsByScheme(Integer schemeId){
		return this.getDao().findByHQL("from Mould this where this.scheme.dbId=? and this.status=?",schemeId,(byte)0);
	}

	
	/**
	 * 发布一个模具流程图(流程版本，只发布一次，平常不用)
	 *  流程图jpdl位置（比如"./com/zb/JBPM/mouldFlow.jpdl.xml"）
	 * @return
	 */
	public String deployFlow(){
		String path="./com/zb/JBPM/mouldFlow.jpdl.xml";
		return flowService.getRepositoryService().createDeployment().addResourceFromClasspath(path).deploy();
	}
	/**
	 * 开启模具订单流程
	 * @param id 模具订单号
	 * @return
	 */
	public ProcessInstance startmouldFlowByKey(String id){
        logger.info("开启模具流程.id={}:",id);
		return flowService.startProcessInstanceByKey("mouldFlow",id);
	}

    public ProcessInstance startmouldProcessFlowByKey(PaiChanMould paiChanMould){
        logger.info("开启排产流程.PaiChanMould={}:",paiChanMould);
        PaiChanRecord paiChanRecord = paiChanMould.getPaiChanRecord();
        Map map = new HashMap();
        map.put("paiChanMouldId",paiChanMould.getDbId());
        //将模具和外协添加到流程中
        map.put("mouldId", paiChanMould.getMould().getDbId());
        //初始化试模次数
        map.put("smcs","0");
        map.put("isReturn",false);
        String key = paiChanRecord.getDbId()+"_"+paiChanMould.getMould().getDbId();
        return flowService.startProcessInstanceByKey("mouldProcess",key,map);
    }
	/**
	 * 开启模具订单流程
	 * @param id 模具订单号
	 * @return
	 */
	public ProcessInstance startmouldFlowByKey(String id,Map map){
		logger.info("开启模具流程.id={}:",id);
		return flowService.startProcessInstanceByKey("mouldFlow",id,map);
	}


	/**
	 * 找到当前的流程实例
	 * @param id
	 * @return
	 */
	public ProcessInstance findProcessInstanceByKey(String id){
		return flowService.findProcessInstanceByKey("mouldFlow", id);
	}
	
	public void delete(Mould mould) throws BaseErrorModel{
		if(mould.getEnable()== 0)
			super.delete(mould);
	}
	
	
	
	/**
	 * 外协购买模具（模具处于订制状态）
	 */
	public void waixieBuy(String taskId,String remark,Admin user) {
        Mould m=this.getMouldInfo(taskId);
		m.setMcomment(remark);
		m.setStatus(MODEL_STATUS.定制.getValue());
		this.update(m);
		flowService.completeTask(taskId,user);

	}

	/**
	 * 收模
	 */
	public void shoumu(String taskId,String remark,Admin user) {
        Mould m=this.getMouldInfo(taskId);
		m.setMcomment(remark);
		m.setStatus(MODEL_STATUS.试模.getValue());
		this.update(m);
		flowService.completeTask(taskId,user);

	}
	
	/**
	 * 添加试模记录
	 */
	public void testModelRecord(String taskId,MouldTestRecord mouldTestRecord,String remark,Admin user) {
        Mould m=this.getMouldInfo(taskId);
		mouldTestRecord.setMould(m);
        mouldTestRecord.setCreateTime(new Date());
        mouldTestRecord.setOperater(user);
		mouldTestRecordDAO.create(mouldTestRecord);
		m.setMcomment(remark);
		m.setStatus(MODEL_STATUS.试模.getValue());
		this.update(m);
		flowService.completeTask(taskId,user);
	}
	
	/**
	 * 模具主管判定
	 */
	public void deside(String taskId,String remark,String nextStep,String scrapId,Admin user) {
		Mould m=this.getMouldInfo(taskId);
		m.setMcomment(remark);
		if(nextStep.equals("成功"))
			m.setStatus(MODEL_STATUS.氮化.getValue());
		else if(nextStep.equals("失败"))
			m.setStatus(MODEL_STATUS.试模.getValue());
        else if(nextStep.equals("完全报废")){
            Integer scrapDbId = Integer.parseInt(scrapId);
            dataDictService.loadById(scrapDbId);
            m.setScrap(dataDictService.loadById(scrapDbId));
            m.setStatus(MODEL_STATUS.彻底报废.getValue());
        }
		this.update(m);
		flowService.completeTask(taskId,nextStep,user);
	}
    @Resource
    private MongoTemplate mongoTemplate;

    public BasicDBObject getMouldFlowInfo(int mouldDbId) {
       return mongoTemplate.findOne(new Query(Criteria.where("mould.dbId").is(mouldDbId)),BasicDBObject.class,"mouldFlowCurrent");
    }

    public BasicDBObject getMouldFlowInfo(String taskId) {
        Mould m=this.getMouldInfo(taskId);
        return mongoTemplate.findOne(new Query(Criteria.where("mould.dbId").is(m.getDbId())),BasicDBObject.class,"mouldFlowCurrent");
    }

    public void updateCombination(int mouldDbId,Map maps){
        BasicDBObject o1= getMouldFlowInfo(mouldDbId);
        logger.info("ext mould={}",o1);
        if(o1!=null){
            mongoTemplate.remove(new Query(Criteria.where("mould.dbId").is(mouldDbId)),"mouldFlowCurrent");
            o1.putAll(maps);
            mongoTemplate.insert(o1,"mouldFlowCurrent");
        }else {
            mongoTemplate.insert(maps,"mouldFlowCurrent");
        }
    }

    private void completeByDefault(String taskId,Admin user,Map map,String nextStep){
        completeByDefault(taskId, user, map, nextStep,new HashMap());
    }

    private void completeByDefault(String taskId,Admin user,Map map,String nextStep,Map flowMap){
        Mould m=this.getMouldInfo(taskId);
        String taskName = flowService.getTaskService().getTask(taskId).getName();
        map.put("mould",m);
        map.put("taskId",taskId);
        map.put("taskName",taskName);
        map.put("operaterid",user.getDbId());
        map.put("nextStep",nextStep);
        mongoTemplate.insert(map,"mouldFlow");
        if(StringUtils.isBlank(nextStep))
            flowService.completeTask(taskId,flowMap,user);
        else
            flowService.completeTask(taskId,nextStep,flowMap,user);
        updateCombination(m.getDbId(),map);
    }

    public void fxjxgcccqr(String taskId,Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void fxmjsq(String taskId, Admin user,Map maps,String nextStep) {
        String executionId = flowService.getTaskService().getTask(taskId).getExecutionId();
        HistoryTaskInstanceImpl hi = (HistoryTaskInstanceImpl) flowService.getHistoryService().createHistoryActivityInstanceQuery().executionId(executionId).orderDesc("dbid").list().get(1);
        if (nextStep.equals("驳回")){
            if("(试模)异常模转修处理".equals(hi.getActivityName())){
                nextStep = "驳回转修";
            }else if("新模技术检修处理".equals(hi.getActivityName())){
                nextStep = "驳回检修";
            }
        }
        completeByDefault(taskId, user, maps, nextStep);
    }

    public void fxtmccqr(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void jymjjwqr(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    /**
     * 挤压试模使用
     */
    public void jyscsy(String taskId, Admin user,Map maps,String nextStep) {
        Integer paiChanMouldId = (Integer) flowService.getContentMap(taskId,"paiChanMouldId");
        PaiChanMould paiChanMould = paiChanMouldDAO.getById(paiChanMouldId);
        paiChanMould.setHasJiYa(true);
        paiChanMouldDAO.update(paiChanMould);
        PaiChanRecord paiChanRecord = paiChanMould.getPaiChanRecord();
        completeByDefault(taskId, user, maps, nextStep);
        log.info("paiChanRecordId = {}", paiChanRecord.toString());
        // 当一个排产的所有排模流程通过了挤压那一步，则进入生产流程继续
        if(paiChanRecordDAO.checkJiYaFinished(paiChanRecord)){
            Task task = flowService.getTaskService().getTask(taskId);
            flowService.getExecutionService().signalExecutionById(task.getExecutionId());
        }
    }

    public void mjrcys(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId, user, maps, nextStep);
    }

    public void pmsmqr(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void ppxmcl(String taskId, Admin user,Map maps,String nextStep) {
        if("试模".equals(nextStep)){
            Map flowMap = getMouldFlowInfo(taskId);
            String smcsStr = flowMap.get("smcs")+"";
            int smcs = StringUtils.isEmpty(smcsStr)?0:Integer.parseInt(smcsStr);
            maps.put("smcs",(smcs+1)+"");
        }
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void rczrrfp(String taskId, Admin user,Map maps,String nextStep) {
        Map map = new HashMap();
        map.put("maintenancer",maps.get("maintenancer"));
        completeByDefault(taskId, user, maps, nextStep,map);
    }

    public void xmjhgrk(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void xmjjxcl(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void ycmzxcl(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void bftmsq(String taskId, Admin user,Map maps,String nextStep) {
        String executionId = flowService.getTaskService().getTask(taskId).getExecutionId();
        HistoryTaskInstanceImpl hi = (HistoryTaskInstanceImpl) flowService.getHistoryService().createHistoryActivityInstanceQuery().executionId(executionId).orderDesc("dbid").list().get(1);
        if (nextStep.equals("驳回")){
            if("(试模)异常模转修处理".equals(hi.getActivityName())){
                nextStep = "驳回转修";
            }else if("新模技术检修处理".equals(hi.getActivityName())){
                nextStep = "驳回检修";
            } else if("模具入场验收".equals(hi.getActivityName())){
                nextStep = "驳回验收";
            }
        }
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void ckfm(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void dhclqr(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void dhklqr(String taskId, Admin user,Map maps,String nextStep) {
        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        maps.put("rlsj",dateformat1.format(new Date()));
        completeByDefault(taskId, user, maps, nextStep);
    }

    public void dhxctx(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void jybzsm(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void pmpmcl(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void pmqr(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void scmjhk(String taskId, Admin user,Map maps,String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public void ppsmcl(String taskId, Admin user, Map maps, String nextStep) {
        completeByDefault(taskId,user,maps,nextStep);
    }

    public MiniPageReq loadPaiChanRecord(MiniPageReq page) {
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from PaiChanRecord t where 1=1 ");
        if(StringUtils.isNotEmpty(page.getParameter("code"))){
            String code = page.getParameter("code");
            hql.append("and (t.orderCodes like :key or t.mouldCodes like :key) ");
            queryConditions.add(new QueryCondition("key", "%" + code + "%"));
        }
        if(StringUtils.isNotEmpty(page.getParameter("productTeamId"))){
            String productTeamId = page.getParameter("productTeamId");
            hql.append("and t.productTeamId = :productTeamId ");
            queryConditions.add(new QueryCondition("productTeamId", Integer.parseInt(productTeamId)));
        }
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

    public void paiMo( Integer recordId) {
        PaiChanRecord paiChanRecord = paiChanRecordDAO.getById(recordId);
        String mouldList = paiChanRecord.getMouldIds();
        if(StringUtils.isEmpty(mouldList)){
            throw new BaseErrorModel("请先分配模具","");
        }
        paiChanRecord.setEnable((byte)0);
        paiChanRecordDAO.update(paiChanRecord);
        List<PaiChanMould> list = paiChanRecordDAO.findPaiChanMoulds(paiChanRecord);
        for (PaiChanMould paiChanMould:list){
            startmouldProcessFlowByKey(paiChanMould);
        }

    }

    public void selectMould(String mouldList, Integer recordId) {
        PaiChanRecord paiChanRecord = paiChanRecordDAO.loadById(recordId);
        paiChanRecord.setMouldIds(mouldList);
        String[] mouldIds = mouldList.split(",");
        String mouldCodes ="";
        for(int a =0;a<mouldIds.length;a++){
            Mould mould = getById(Integer.parseInt(mouldIds[a]));
            mouldCodes+=mould.getCode();
            if(a!=mouldIds.length-1){
                mouldCodes+=",";
            }
        }
        paiChanRecord.setMouldCodes(mouldCodes);
        paiChanRecordDAO.mouldUpdate(paiChanRecord);
    }
}
