package com.zb.jnlxc.service;

import java.util.*;

import javax.annotation.Resource;

import com.ZLHW.base.dao.QueryCondition;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessInstance;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.MouldDAO;
import com.zb.jnlxc.dao.MouldRecordDAO;
import com.zb.jnlxc.dao.MouldTestRecordDAO;
import com.zb.jnlxc.dao.SchemeDAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.Mould.MODEL_STATUS;
import com.zb.jnlxc.model.MouldTestRecord;
import com.zb.jnlxc.model.Scheme;

@Transactional
@Service
public class MouldService extends BaseService<MouldDAO,Mould, Integer>{
	private static final Log log = LogFactory.getLog(MouldService.class);
	@Resource
	private SchemeDAO schemeDao;
	@Resource
	private FlowService flowService;
	@Resource
	private MouldTestRecordDAO mouldTestRecordDAO;
    @Resource
    private MouldRecordDAO mouldRecordDAO;
    @Resource
    DataDictService dataDictService;
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
		Map map = new HashMap();
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
		
		//将模具和外协添加到流程中
		map.put("mouldId", mould.getDbId());
		map.put("association", mould.getManufacture().getAgent().getAccount());
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
		return this.getDao().findByHQL("from Mould this where this.scheme.dbId=? and this.status=?",(Object)schemeId,(byte)0);
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
		return flowService.startProcessInstanceByKey("mouldFlow",id);
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

    public BasicDBObject getMouldFlowInfo(String taskId) {
        Mould m=this.getMouldInfo(taskId);
       return mongoTemplate.findOne(new Query(Criteria.where("mould.dbId").is(m.getDbId())),BasicDBObject.class,"mouldFlowCurrent");
    }

    public void updateCombination(String taskId,Map<String,String> maps){
        BasicDBObject o1= getMouldFlowInfo(taskId);
        mongoTemplate.remove(o1,"mouldFlowCurrent");
        o1.putAll(maps);
        mongoTemplate.insert(o1,"mouldFlowCurrent");
    }

    public void fxjxgcccqr(String taskId, String remarks, Admin user,Map<String,String> maps) {
        Mould m=this.getMouldInfo(taskId);
        BasicDBObject dbObject = new BasicDBObject(maps);
        dbObject.put("mould",m);
        dbObject.put("taskId",taskId);

        mongoTemplate.insert(dbObject);
        updateCombination(taskId,maps);
        flowService.completeTask(taskId,user);
    }

    public void fxmjsq(String taskId, String remarks, Admin user) {
    }

    public void fxtmccqr(String taskId, String remarks, Admin user) {
    }

    public void jymjjwqr(String taskId, String remarks, Admin user) {
    }

    public void jyscsy(String taskId, String remarks, Admin user) {
    }

    public void mjrcys(String taskId, String remarks, Admin user) {
    }

    public void pmsmqr(String taskId, String remarks, Admin user) {
    }

    public void ppxmcl(String taskId, String remarks, Admin user) {
    }

    public void rczrrfp(String taskId, String remarks, Admin user) {
    }

    public void xmjhgrk(String taskId, String remarks, Admin user) {
    }

    public void xmjjxcl(String taskId, String remarks, Admin user) {
    }

    public void ycmzxcl(String taskId, String remarks, Admin user) {
    }

    public void bftmsq(String taskId, String remarks, Admin user) {
    }

    public void ckfm(String taskId, String remarks, Admin user) {
    }

    public void dhclqr(String taskId, String remarks, Admin user) {
    }

    public void dhklqr(String taskId, String remarks, Admin user) {
    }

    public void dhxctx(String taskId, String remarks, Admin user) {
    }

    public void jybzsm(String taskId, String remarks, Admin user) {
    }

    public void pmpmcl(String taskId, String remarks, Admin user) {
    }

    public void pmqr(String taskId, String remarks, Admin user) {
    }

    public void scmjhk(String taskId, String remarks, Admin user) {
    }


}
