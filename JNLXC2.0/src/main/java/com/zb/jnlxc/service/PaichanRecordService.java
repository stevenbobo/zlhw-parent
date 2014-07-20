package com.zb.jnlxc.service;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.form.ProductTrace;
import com.zb.jnlxc.model.*;
import com.zb.util.StringUtils;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Transactional
@Service
public class PaichanRecordService extends BaseService<PaiChanRecordDAO,PaiChanRecord, Integer> {
	private static final Log log = LogFactory.getLog(PaichanRecordService.class);
	@Resource
	private OrderFormDAO orderFormDAO;
	@Resource
	private ReportService reportService;
	@Resource
	private OrderDetailDAO orderDetailDAO;
    @Resource
    private PaichanOrderDetailDAO paichanOrderDetailDAO;
    @Resource
    private ProductRecordDetailHistoryDAO productRecordDetailHistoryDAO;
	@Resource
	private ProductTeamService productTeamService;
	@Value("${print.server.url}")
	private String printServerUrl;
	@Value("${print.server.port}")
	private int printServerPort;
	@Resource
	FlowService flowService;
    @Resource
    AdminDAO adminDAO;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private SubPaichanRecordDAO subPaichanRecordDAO;

	/**
	 * 通过任务id查询生产记录
	 * @param taskId
	 * @return
	 */
	public SubPaichanRecord getSubPaiChanRecordByTaskId(String taskId){
		Integer paichanRecordId = (Integer) flowService.getContentMap(taskId, "subPaichanRecordId");
		return subPaichanRecordDAO.getById(paichanRecordId);
	}

    public List<ProductTrace> getProductTrace(String taskId){
        SubPaichanRecord subPaichanRecord = getSubPaiChanRecordByTaskId(taskId);
        PaiChanRecord paiChanRecord = subPaichanRecord.getPaiChanRecord();
        OrderForm orderForm = paiChanRecord.getOrderForm();
        List<PaichanOrderDetail> paichanOrderDetailList =
                paichanOrderDetailDAO.findByPaichan(paiChanRecord.getDbId());

        List<ProductTrace> results = new ArrayList<ProductTrace>();

        for (PaichanOrderDetail paichanOrderDetail:paichanOrderDetailList){
            ProductTrace productTrace = new ProductTrace();
            productTrace.setFinishQuantiy(paichanOrderDetail.getCompQuantity());
            productTrace.setFinishWeight(paichanOrderDetail.getCompWeight());
            OrderDetail orderDetail = paichanOrderDetail.getOrderDetail();
            productTrace.setAginghardness(subPaichanRecord.getAginghardness());
            productTrace.setOxideFilm(orderForm.getOxideFilm());
            productTrace.setAlHeatNum(subPaichanRecord.getAlHeatNum());
            productTrace.setOrderQuantity(paichanOrderDetail.getOrderQuantity());
            productTrace.setOrderWeight(paichanOrderDetail.getOrderWeight());
            productTrace.setUncompleteQuantity(paichanOrderDetail.getOrderQuantity()-paichanOrderDetail.getCompQuantity());
            productTrace.setUncompleteWeight(paichanOrderDetail.getOrderWeight()-paichanOrderDetail.getCompWeight());
            productTrace.setDbId(orderDetail.getDbId());
            productTrace.setOrderDetail(orderDetail);
            productTrace.setSetSize(orderDetail.getSetSize());
            productTrace.setAlHeatNum(subPaichanRecord.getAlHeatNum());
            productTrace.setPack(orderDetail.getOrderForm().getPack());
            productTrace.setPreSendDate(orderDetail.getOrderForm().getPreSendDate());
            productTrace.setEnough(orderDetail.getCompStatus());
            results.add(productTrace);
        }
        return results;
    }

	/**
	 * 分页载入生产记录
	 * @param page
	 * @return
	 */
	public Page loadProductRecordByPage(Page page) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<QueryCondition> queryConditions=new ArrayList();
        StringBuilder hql = new StringBuilder("from PaiChanRecord t where 1=1 ");
        if (StringUtils.isNotEmpty(page.getParameter("orderId"))){
            Integer orderId = Integer.parseInt(page.getParameter("orderId"));
            hql.append("and t.orderForm.dbId = :orderId ");
            queryConditions.add(new QueryCondition("orderId", orderId));
        }
        if (StringUtils.isNotEmpty(page.getParameter("code"))){
            hql.append("and t.code like :code ");
            queryConditions.add(new QueryCondition("code", "%"+page.getParameter("code")+"%"));
        }
        if (StringUtils.isNotEmpty(page.getParameter("orderCode"))){
            hql.append("and t.orderForm.code like :orderCode ");
            queryConditions.add(new QueryCondition("orderCode", "%"+page.getParameter("orderCode")+"%"));
        }
        if(StringUtils.isNotEmpty(page.getParameter("startDate"))){
            hql.append("and t.createDate >=:startDate ");
            Date startDate=sdf.parse(page.getParameter("startDate"));
            queryConditions.add(new QueryCondition("startDate", startDate));
        }
        if(StringUtils.isNotEmpty(page.getParameter("endDate"))){
            hql.append("and t.createDate <:endDate ");
            Date endDate=sdf.parse(page.getParameter("endDate"));
            queryConditions.add(new QueryCondition("endDate", endDate));
        }
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
		return page;
	}

    public void checkOrderState(OrderForm orderForm){
        if(orderForm.getCurrentState()==2)
            throw new BaseErrorModel("流程暂停，无法操作", "");
    }

    public void checkOrderState(String taskId){
        OrderForm orderForm = getOrderFormInfo(taskId);
        if(orderForm.getCurrentState()==2)
            throw new BaseErrorModel("流程暂停，无法操作", "");
    }



	public void startProductRecordFlow(PaiChanRecord paichanRecord) throws BaseErrorModel{
		OrderForm orderForm=paichanRecord.getOrderForm();
        checkOrderState(orderForm);
		if(orderForm.getCompStatus()==(byte)1){
			throw new BaseErrorModel("该订单已完成,无法开启跟踪单流程", "");
		}
		// 开启生产记录流程
		Map map=new HashMap();
        map.put("paichanRecordId",paichanRecord.getDbId());
		map.put("orderFormId", orderForm.getDbId());
		orderForm.setNextRecordNum(orderForm.getNextRecordNum()+1);
		orderFormDAO.update(orderForm);
        checkOrderState(orderForm);
        List<String> squence=orderForm.generateStepList();
        String nextStep = squence.get(0);
        paichanRecord.setNextStep(nextStep);
        super.create(paichanRecord);
		this.startProductRecordFlowByKey(paichanRecord.getCode(), map);
	}


    public void updateOrderMcomment(String taskId,String wcomment){
        OrderForm orderForm = getOrderFormInfo(taskId);
        orderForm.setMcomment(wcomment);
        orderFormDAO.update(orderForm);
    }
	/**
	 * 从流程中取得订单信息
	 * @param taskId
	 * @return
	 */
	public OrderForm getOrderFormInfo(String taskId){
		Integer orderFormId=(Integer)flowService.getContentMap(taskId, "orderFormId");
		return orderFormDAO.getById(orderFormId);
	}


	/**
	 * 打印生产跟踪单并开启流程，保存生产记录
	 * @param taskId
	 * @return
	 * @throws net.sf.jasperreports.engine.JRException
	 * @throws com.ZLHW.base.Exception.BaseErrorModel
	 */
	public void printForm(String taskId,String comment,Admin user) throws JRException, BaseErrorModel  {
        updateOrderMcomment(taskId,comment);
		flowService.completeTask(taskId,user);
	}

	/**
	 * 输出PDF流
	 * @param taskId
	 * @throws net.sf.jasperreports.engine.JRException
	 * @throws org.dom4j.DocumentException
	 */
	public void startCreatePDF(String taskId,OutputStream os) throws JRException, DocumentException{
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId).getPaiChanRecord();
		reportService.exportProductRecord(paiChanRecord,os);
	}
	//生产部审核
	public void productionAudit(String taskId,String wcomment,Admin user) throws BaseErrorModel{
        updateOrderMcomment(taskId,wcomment);
		flowService.completeTask(taskId,user);
	}


    public void recordForPaichan(PaiChanRecord paiChanRecord,Admin admin){
        List<PaichanOrderDetail> l = paichanOrderDetailDAO.findByPaichan(paiChanRecord.getDbId());
        Map map = new HashMap();
        map.put("paiChanRecord",paiChanRecord);
        map.put("paichanOrderDetails",l);
        map.put("taskName",paiChanRecord.getCurrentStep());
        map.put("operaterid",admin.getDbId());
        map.put("operaterName",admin.getName());
        mongoTemplate.insert(map,"mouldFlow");
    }


    //完成挤压任务
    public void jiya(String taskId,List<ProductTrace> traces,String aginghardness,String alHeatNum,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        SubPaichanRecord subPaiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        updateProductDetail(traces,subPaiChanRecord.getPaiChanRecord(),user,"挤压");
        subPaiChanRecord.setAginger(user);
        subPaiChanRecord.setAginghardness(aginghardness);
        subPaiChanRecord.setAlHeatNum(alHeatNum);
        subPaiChanRecord.setWcomment(wcomment);
        subPaiChanRecord.setCurrentStep("挤压");
        List<String> squence=subPaiChanRecord.getOrderForm().generateStepList();
        String nextStep = squence.get(0);
        subPaiChanRecord.setNextStep(nextStep);
        this.update(subPaiChanRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, user);
        recordForPaichan(subPaiChanRecord,user);
    }



	//完成时效任务
	public void shiXiao(String taskId,List<ProductTrace> traces,String aginghardness,String alHeatNum,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        updateProductDetail(traces,paiChanRecord,user,"时效");
        paiChanRecord.setAginger(user);
        paiChanRecord.setAginghardness(aginghardness);
        paiChanRecord.setAlHeatNum(alHeatNum);
        paiChanRecord.setWcomment(wcomment);
        paiChanRecord.setCurrentStep("时效");
        String nextStep = paiChanRecord.getOrderForm().getSurfaceProcess().getStr1();
        nextStep=StringUtils.isEmpty(nextStep)?"打包":nextStep;
        paiChanRecord.setNextStep(nextStep);
        this.update(paiChanRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, user);
        recordForPaichan(paiChanRecord,user);
	}
	//完成氧化任务
	public void yangHua(String taskId,List<ProductTrace> traces,String oxiFilm,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        boolean ofcomplete = updateProductDetail(traces,paiChanRecord,user,"氧化");
        paiChanRecord.setOxiFilm(oxiFilm);
        paiChanRecord.setOxifilmer(user);
        paiChanRecord.setWcomment(wcomment);
        paiChanRecord.setCurrentStep("氧化");
        String nextStep = paiChanRecord.getOrderForm().getSurfaceProcess().getStr2();
        nextStep=StringUtils.isEmpty(nextStep)?"打包":nextStep;
        paiChanRecord.setNextStep(nextStep);

        this.update(paiChanRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, user);
        recordForPaichan(paiChanRecord,user);
	}
	//完成电泳任务
	public void dianYong(String taskId,List<ProductTrace> traces,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        boolean ofcomplete = updateProductDetail(traces,paiChanRecord,user,"电泳");
        paiChanRecord.setElectrophoresis(user);
        paiChanRecord.setWcomment(wcomment);
        paiChanRecord.setCurrentStep("电泳");
        String nextStep = "打包";
        paiChanRecord.setNextStep(nextStep);
        this.update(paiChanRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, user);
        recordForPaichan(paiChanRecord,user);
	}
	//完成喷涂任务
	public void plating(String taskId,List<ProductTrace> traces,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        updateProductDetail(traces,paiChanRecord,user,"喷涂");
        paiChanRecord.setPlater(user);
        paiChanRecord.setWcomment(wcomment);
        paiChanRecord.setCurrentStep("喷涂");
        String nextStep = "打包";
        paiChanRecord.setNextStep(nextStep);
        this.update(paiChanRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, user);
        recordForPaichan(paiChanRecord,user);
	}
	//完成打包任务
	public void pack(String taskId,List<ProductTrace> traces, String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer paichanRecordId=(Integer)flowService.getContentMap(taskId, "paichanRecordId");
        PaiChanRecord paiChanRecord = this.loadById(paichanRecordId);
        boolean ofcomplete = updateProductDetail(traces,paiChanRecord,user,"打包");
        paiChanRecord.setPackager(user);
        paiChanRecord.setWcomment(wcomment);
		this.update(paiChanRecord);
        //如果未完成，开启另外一张跟踪单
//        if(!ofcomplete) {
//            this.startProductRecordFlow(dbProductRecord.getOrderForm().getDbId(),productTeamId);
//        }
        flowService.completeTask(taskId,user);
        recordForPaichan(paiChanRecord,user);
    }
	//仓管记录存放位置
	public void storage(String taskId,List<ProductTrace> traces,String storeLocation,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        updateProductDetail(traces,paiChanRecord,user,"仓管记录存放位置");
        paiChanRecord.setWarehouser(user);
        paiChanRecord.setStoreLocation(storeLocation);
        paiChanRecord.setWcomment(wcomment);
		this.update(paiChanRecord);
        OrderForm orderForm = paiChanRecord.getOrderForm();
        orderForm.setMcomment(wcomment);
        orderFormDAO.update(orderForm);
        flowService.completeTask(taskId,user);
        recordForPaichan(paiChanRecord,user);
    }
	//发货员发货
	public void sendProduct(String taskId,List<ProductTrace> traces,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        updateProductDetail(traces,paiChanRecord,user,"发货员发货");
        paiChanRecord.setSender(user);
        paiChanRecord.setWcomment(wcomment);
		this.update(paiChanRecord);
        OrderForm orderForm = paiChanRecord.getOrderForm();
        orderForm.setMcomment(wcomment);
        orderFormDAO.update(orderForm);
        flowService.completeTask(taskId,user);
        recordForPaichan(paiChanRecord,user);
    }
	//财务核对
	public void financialReconciliation(String taskId,int totalPrice,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        paiChanRecord.setTotalPrice(totalPrice);
        paiChanRecord.setFinancer(user);
        paiChanRecord.setWcomment(wcomment);
		this.update(paiChanRecord);
        OrderForm orderForm = paiChanRecord.getOrderForm();
        orderForm.setMcomment(wcomment);
        orderFormDAO.update(orderForm);
        flowService.completeTask(taskId,user);
        recordForPaichan(paiChanRecord,user);
    }

	/**
	 * 完成过磅任务
	 * @param taskId 任务号
	 * @param wcomment 备注
	 * @throws com.ZLHW.base.Exception.BaseErrorModel
	 */
	public void weight(String taskId,List<ProductTrace> traces,String wcomment,Admin user) throws BaseErrorModel {
        checkOrderState(taskId);
        checkWeight(traces);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        OrderForm orderForm = paiChanRecord.getOrderForm();
        paiChanRecord.setWcomment(wcomment);
        orderForm.setMcomment(wcomment);
        updateProductDetail(traces,paiChanRecord,user,"过磅");
        paiChanRecord.setWeighter(user);//记录当前操作用户为过磅员
		this.update(paiChanRecord);
		flowService.completeTask(taskId,user);
        recordForPaichan(paiChanRecord,user);
	}


    public void transit(String taskId, List<ProductTrace> traces, String wcomment, Admin user) {
        checkOrderState(taskId);
        checkQuantity(traces);
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
        OrderForm orderForm = paiChanRecord.getOrderForm();
        paiChanRecord.setWcomment(wcomment);
        orderForm.setMcomment(wcomment);
        updateProductDetail(traces,paiChanRecord,user,"中转");
        paiChanRecord.setTransiter(user);//记录当前操作用户为过磅员
        this.update(paiChanRecord);
        flowService.completeTask(taskId,user);
        recordForPaichan(paiChanRecord,user);
    }

    private void checkWeight(List<ProductTrace> traces){
        int weight = 0;
        for(ProductTrace productTrace:traces){
             weight  += productTrace.getFinishWeight();
        }
        if(weight==0){
            throw new BaseErrorModel("重量不能为0","");
        }
    }

    private void checkQuantity(List<ProductTrace> traces){
        int quantity = 0;
        for(ProductTrace productTrace:traces){
            quantity  += productTrace.getFinishQuantiy();
        }
        if(quantity==0){
            throw new BaseErrorModel("数量不能为0","");
        }
    }

    private boolean updateProductDetail(List<ProductTrace> traces,PaiChanRecord paiChanRecord,Admin user,String taskName){
        OrderForm orderForm=paiChanRecord.getOrderForm();
        this.getDao().refresh(orderForm);
        boolean ofcomplete = true;
        for (ProductTrace pt : traces) {
            OrderDetail od = orderDetailDAO.loadById(pt.getDbId());
            PaichanOrderDetail paichanOrderDetail = paichanOrderDetailDAO.getByOrderDetail(pt.getDbId(),paiChanRecord.getDbId());
            paichanOrderDetail.setCompWeight(pt.getFinishWeight());
            paichanOrderDetail.setCompQuantity(pt.getFinishQuantiy());
            ofcomplete=ofcomplete&pt.getEnough()!=(byte)0;
            paichanOrderDetailDAO.update(paichanOrderDetail);
            orderDetailDAO.updateCompWeight(od);
            productRecordDetailHistoryDAO.createByProductDetail(paichanOrderDetail,taskName,user);

        }
        orderForm.setCompStatus(ofcomplete?(byte)1:(byte)0);
        //修改订单
        orderFormDAO.updateCompWeight(orderForm);
        return ofcomplete;
    }

	/**
	 * 删除生产记录的同时，更新订单以及订单明细
	 */
	public void delete(PaiChanRecord paiChanRecord) throws BaseErrorModel{
        flowService.deleteProcessInstanceCascade("productRecordFlow."+paiChanRecord.getCode());
        delete(paiChanRecord);
	}
	/**
	 * 删除正在执行的跟踪单流程
	 * @param id
	 * @throws com.ZLHW.base.Exception.BaseErrorModel
	 */
	public void deleteProductFlowInstanceCascade(String id) throws BaseErrorModel{
		Execution execution=flowService.getExecutionService().findExecutionById(id);
		Integer paichanRecordId=(Integer) flowService.getExecutionService().getVariable(id, "paichanRecordId");
		if(paichanRecordId!=null){
		PaiChanRecord paiChanRecord=this.getById(paichanRecordId);
		if(paiChanRecord!=null){
			this.delete(paiChanRecord);
		}
		}
		flowService.deleteProcessInstanceCascade(id);
	}

	/**
	 * 发布一个跟踪单流程图(流程版本，只发布一次，平常不用)
	 * 流程图jpdl位置（比如"./com/zb/JBPM/orderFlow.jpdl.xml"）
	 * @return
	 */
	public String deployFlow() {
		String path = "./com/zb/JBPM/productRecordFlow.jpdl.xml";
		return flowService.getRepositoryService().createDeployment()
				.addResourceFromClasspath(path).deploy();
	}
	
	/**
	 * 用于前台获取跟踪单信息
	 * @param taskId
	 * @return
	 */
	public Map getProductRecordInfo(String taskId){
        PaiChanRecord paiChanRecord = getSubPaiChanRecordByTaskId(taskId);
		OrderForm orderForm=paiChanRecord.getOrderForm();

		Map map=new HashMap();
		map.put("orderForm", orderForm);
		map.put("productTeamId",paiChanRecord.getProductTeam().getDbId());
		map.put("productNum", orderForm.getNextRecordNum());
		map.put("productCode", paiChanRecord.getCode());
		map.put("productRecord", paiChanRecord);
		return map;
	}

	/**
	 * 开启跟踪单流程
	 * 
	 * @param id
	 *            模具订单号
	 * @return
	 */
	public ProcessInstance startProductRecordFlowByKey(String id) {
		return flowService.startProcessInstanceByKey("productRecordFlow", id);
	}

	/**
	 * 开启跟踪单流程
	 * 
	 * @param id
	 *            模具订单号
	 * @return
	 */
	public ProcessInstance startProductRecordFlowByKey(String id, Map map) {
		return flowService.startProcessInstanceByKey("productRecordFlow", id, map);
	}

	/**
	 * 找到当前的流程实例
	 * 
	 * @param id
	 * @return
	 */
	public ProcessInstance findProductRecordFlowInstanceByKey(String id) {
		return flowService.findProcessInstanceByKey("productRecordFlow", id);
	}


    public List<ProductRecordDetailHistory> getWorkDetail(int productId) {
        String hql =  "from ProductRecordDetailHistory t " +
                "where t.paichanRecord_dbId=? order by t.dbId asc";
        return productRecordDetailHistoryDAO.findByHQL(hql,productId);
    }

}
