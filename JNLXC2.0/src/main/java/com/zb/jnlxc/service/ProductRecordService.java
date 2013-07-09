package com.zb.jnlxc.service;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;


@Transactional
@Service
public class ProductRecordService extends BaseService<ProductRecordDAO,ProductRecord, Integer> {
	private static final Log log = LogFactory.getLog(ProductRecordService.class);
	@Resource
	private ProductRecordDetailDAO productRecordDetailDAO;
    @Resource
    private ProductTeamDAO productTeamDAO;
	@Resource
	private OrderFormDAO orderFormDAO;
	@Resource
	private ReportService reportService;
	@Resource
	private OrderDetailDAO orderDetailDAO;
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
	
	/**
	 * 通过任务id查询生产记录
	 * @param taskId
	 * @return
	 */
	public ProductRecord getProductRecordByTaskId(String taskId){
		Integer productRecordId = (Integer) flowService.getContentMap(taskId, "productRecordId");
		if(productRecordId!=null)
			return this.getById(productRecordId);
		else{//生成虚拟生产记录与前台交互
			OrderForm orderForm = orderFormDAO.getById(
					(Integer) flowService.getContentMap(taskId, "orderFormId"));

			String wcomment=(String)flowService.getContentMap(taskId, "wcomment");
			ProductRecord productRecord = new ProductRecord();
			productRecord.setRecordNum(orderForm.getNextRecordNum());
			productRecord.setCode(orderForm.getCode()+String.format("%02d", orderForm.getNextRecordNum()));//设置跟踪单编码
			Integer productTeamId = (Integer)flowService.getContentMap(taskId, "productTeamId");
			ProductTeam productTeam=null;
			if(productTeamId!=null)
				productTeam = productTeamService.getById(productTeamId);
			if(productTeam!=null)
				productRecord.setCharger(adminDAO.loadById(productTeam.getCharge_dbId()));//设置机台负责人
			productRecord.setOrderForm(orderForm);//对应的订单
			productRecord.setNitrideNum(0);//已完成数量为0
			productRecord.setCreateDate(new Date(System.currentTimeMillis()));//设置开跟踪单时间
			productRecord.setWcomment(wcomment);//设置评论
			return productRecord;
		}
	}

    public List<ProductTrace> getProductTrace(String taskId){
        ProductRecord productRecord = getProductRecordByTaskId(taskId);
        Integer orderFormId = (Integer) flowService.getContentMap(taskId, "orderFormId");
        Integer productRecordId = (Integer) flowService.getContentMap(taskId, "productRecordId");
        List<ProductTrace> results = new ArrayList<ProductTrace>();
        OrderForm orderForm = orderFormDAO.getById(orderFormId);
        List<OrderDetail> orderDetails =  orderDetailDAO.getByOrderForm(orderForm);

        for (OrderDetail orderDetail:orderDetails){
            ProductTrace productTrace = new ProductTrace();
            Map<String,Long> completeInfo =productRecordDetailDAO.getCompDetail(orderDetail);
            Integer compWeight =  completeInfo.get("compWeight").intValue();
            Integer compQuantity =  completeInfo.get("compQuantity").intValue();
            productTrace.setFinishQuantiy(0);
            productTrace.setFinishWeight(0);
            if(productRecordId!=null) {
                ProductRecordDetail prd = productRecordDetailDAO.getByOrderDetail(orderDetail,productRecord);
                if(prd!=null){
                    productTrace.setFinishQuantiy(prd.getDetailQuantity());
                    productTrace.setFinishWeight(prd.getDetailWeight());
                }
            }
            productTrace.setUncompleteQuantity(orderDetail.getOrderQuantity()-compQuantity+productTrace.getFinishQuantiy());
            productTrace.setUncompleteWeight(orderDetail.getOrderWeight()-compWeight+productTrace.getFinishWeight());
            productTrace.setDbId(orderDetail.getDbId());
            productTrace.setOrderDetail(orderDetail);
            productTrace.setSetSize(orderDetail.getSetSize());
            productTrace.setAlHeatNum(productRecord.getAlHeatNum());
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
        StringBuilder hql = new StringBuilder("from ProductRecord t where 1=1 ");
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


	
	public void startProductRecordFlow(Integer orderFormId) throws BaseErrorModel{
		OrderForm orderForm=orderFormDAO.getById(orderFormId);
        checkOrderState(orderForm);
		if(orderForm.getCompStatus()==(byte)1){
			throw new BaseErrorModel("该订单已完成,无法开启跟踪单流程", "");
		}
		String code=orderForm.getCode()+String.format("%02d", orderForm.getNextRecordNum());
		// 开启生产记录流程
		Map map=new HashMap();
		map.put("orderFormId", orderForm.getDbId());
		map.put("productNum", orderForm.getNextRecordNum());
		map.put("productCode", code);
		List<ProductRecord> plist=this.getDao().findByHQL("from ProductRecord p where p.orderForm=?", orderForm);
		for(ProductRecord productRecord:plist){
			List<ProductRecordDetail> detaillist=productRecordDetailDAO.getByProductRecord(productRecord);
			if(detaillist.size()==0)
				throw new BaseErrorModel("编号为"+productRecord.getCode()+"的生产跟踪单未经过过磅，\n 请先过磅", "");
		}
		orderForm.setNextRecordNum(orderForm.getNextRecordNum()+1);
		orderFormDAO.update(orderForm);
		this.startProductRecordFlowByKey(code, map);
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
	 * @throws JRException 
	 * @throws BaseErrorModel 
	 */
	public ProductRecord printForm(String taskId,String comment,Admin user) throws JRException, BaseErrorModel  {
        checkOrderState(taskId);
		Integer orderFormId = (Integer) flowService.getContentMap(taskId,"orderFormId");
		OrderForm orderForm=orderFormDAO.getById(orderFormId);
		Integer productNum=(Integer) flowService.getContentMap(taskId, "productNum");
		String productCode=(String) flowService.getContentMap(taskId, "productCode");
		ProductRecord productRecord = new ProductRecord();
		productRecord.setRecordNum(productNum);
		productRecord.setCode(productCode);//设置跟踪单编码
        ProductTeam productTeam = getProductTeamFormFlow(taskId);
		productRecord.setCharger(adminDAO.loadById(productTeam.getCharge_dbId()));//设置机台负责人
		productRecord.setOrderForm(orderForm);//对应的订单
		productRecord.setProductTeam(productTeam);//设置生产机台
		productRecord.setNitrideNum(0);//已完成数量为0
		productRecord.setCreateDate(new Date());//设置开跟踪单时间
		productRecord.setCurrentState((byte)1);
		productRecord.setSurfaceProcess(orderForm.getSurfaceProcess());
		productRecord.setWcomment(comment);
		Map map = new HashMap();
		super.create(productRecord);
		map.put("productRecordId", productRecord.getDbId());
		flowService.completeTask(taskId,map,user);
		// 开启生产记录流程
		return productRecord;
	}

    /**
     * 获取机台
     * @param taskId
     * @return
     */
    private ProductTeam getProductTeamFormFlow(String taskId){
        checkOrderState(taskId);
        Integer productTeamId = (Integer)flowService.getContentMap(taskId, "productTeamId");
        ProductTeam productTeam=null;
        if(productTeamId!=null)
            productTeam = productTeamService.getById(productTeamId);
        return productTeam;
    }
	/**
	 * 输出PDF流
	 * @param taskId
	 * @throws JRException
	 * @throws DocumentException 
	 */
	public void startCreatePDF(String taskId,OutputStream os) throws JRException, DocumentException{
		Integer orderFormId = (Integer) flowService.getContentMap(taskId,"orderFormId");
		OrderForm orderForm=orderFormDAO.getById(orderFormId);


        ProductRecord productRecord = new ProductRecord();
        productRecord.setRecordNum(orderForm.getNextRecordNum());
        productRecord.setCode(orderForm.getCode()+String.format("%02d", orderForm.getNextRecordNum()));//设置跟踪单编码

        ProductTeam productTeam = getProductTeamFormFlow(taskId);
        if(productTeam!=null)
            productRecord.setCharger(adminDAO.loadById(productTeam.getCharge_dbId()));//设置机台负责人

		productRecord.setOrderForm(orderForm);//对应的订单
		productRecord.setNitrideNum(0);//已完成数量为0
		productRecord.setCreateDate(new Date(System.currentTimeMillis()));//设置开跟踪单时间
		reportService.exportProductRecord(productRecord,os);
	}
	//生产部审核
	public void productionAudit(String taskId,String wcomment,Admin user) throws BaseErrorModel{
        updateOrderMcomment(taskId,wcomment);
		flowService.completeTask(taskId,user);
	}


	//车间分配机台
	public void workshopTask(String taskId,Integer productTeamId,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        updateOrderMcomment(taskId,wcomment);
        Map map = new HashMap();
		map.put("productTeamId", productTeamId);
		flowService.completeTask(taskId,map,user);
	}
	//完成时效任务
	public void shiXiao(String taskId,List<ProductTrace> traces,String aginghardness,String alHeatNum,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer productRecordId=(Integer)flowService.getContentMap(taskId, "productRecordId");
		ProductRecord dbProductRecord=this.loadById(productRecordId);
        boolean ofcomplete = updateProductDetail(traces,dbProductRecord);
        dbProductRecord.setAginger(user);
		dbProductRecord.setAginghardness(aginghardness);
		dbProductRecord.setAlHeatNum(alHeatNum);
		dbProductRecord.setWcomment(wcomment);
        String nextStep = dbProductRecord.getOrderForm().getSurfaceProcess().getStr1();
        nextStep=StringUtils.isEmpty(nextStep)?"打包":nextStep;
        this.update(dbProductRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, nextStep, user);
	}
	//完成氧化任务
	public void yangHua(String taskId,List<ProductTrace> traces,String oxiFilm,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer productRecordId=(Integer)flowService.getContentMap(taskId, "productRecordId");
		ProductRecord dbProductRecord=this.loadById(productRecordId);
        boolean ofcomplete = updateProductDetail(traces,dbProductRecord);
        dbProductRecord.setOxiFilm(oxiFilm);
		dbProductRecord.setOxifilmer(user);
		dbProductRecord.setWcomment(wcomment);
        String nextStep = dbProductRecord.getOrderForm().getSurfaceProcess().getStr2();
        nextStep=StringUtils.isEmpty(nextStep)?"打包":nextStep;
        this.update(dbProductRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, nextStep, user);
	}
	//完成电泳任务
	public void dianYong(String taskId,List<ProductTrace> traces,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer productRecordId=(Integer)flowService.getContentMap(taskId, "productRecordId");
		ProductRecord dbProductRecord=this.loadById(productRecordId);
        boolean ofcomplete = updateProductDetail(traces,dbProductRecord);
        dbProductRecord.setElectrophoresis(user);
		dbProductRecord.setWcomment(wcomment);
        this.update(dbProductRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, "打包", user);
	}
	//完成喷涂任务
	public void plating(String taskId,List<ProductTrace> traces,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer productRecordId=(Integer)flowService.getContentMap(taskId, "productRecordId");
		ProductRecord dbProductRecord=this.loadById(productRecordId);
        boolean ofcomplete = updateProductDetail(traces,dbProductRecord);
		dbProductRecord.setPlater(user);
		dbProductRecord.setWcomment(wcomment);
        this.update(dbProductRecord);
        updateOrderMcomment(taskId, wcomment);
        flowService.completeTask(taskId, "打包", user);
	}
	//完成打包任务
	public void pack(String taskId,List<ProductTrace> traces, String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer productRecordId=(Integer)flowService.getContentMap(taskId, "productRecordId");
		ProductRecord dbProductRecord=this.loadById(productRecordId);
        boolean ofcomplete = updateProductDetail(traces,dbProductRecord);
		dbProductRecord.setPackager(user);
		dbProductRecord.setWcomment(wcomment);
		this.update(dbProductRecord);
        //如果未完成，开启另外一张跟踪单
        if(!ofcomplete) {
            this.startProductRecordFlow(dbProductRecord.getOrderForm().getDbId());
        }
        OrderForm orderForm = dbProductRecord.getOrderForm();
        orderForm.setMcomment(wcomment);
        orderFormDAO.update(orderForm);
        flowService.completeTask(taskId,user);
    }
	//仓管记录存放位置
	public void storage(String taskId,String storeLocation,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer productRecordId=(Integer)flowService.getContentMap(taskId, "productRecordId");
		ProductRecord dbProductRecord=this.loadById(productRecordId);
		dbProductRecord.setWarehouser(user);
        dbProductRecord.setStoreLocation(storeLocation);
		dbProductRecord.setWcomment(wcomment);
		this.update(dbProductRecord);
        OrderForm orderForm = dbProductRecord.getOrderForm();
        orderForm.setMcomment(wcomment);
        orderFormDAO.update(orderForm);
        flowService.completeTask(taskId,user);
    }
	//发货员发货
	public void sendProduct(String taskId,String finalWeight,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer productRecordId=(Integer)flowService.getContentMap(taskId, "productRecordId");
		ProductRecord dbProductRecord=this.loadById(productRecordId);
		dbProductRecord.setSender(user);
		dbProductRecord.setFinalWeight(finalWeight);
		dbProductRecord.setWcomment(wcomment);
		this.update(dbProductRecord);
        OrderForm orderForm = dbProductRecord.getOrderForm();
        orderForm.setMcomment(wcomment);
        orderFormDAO.update(orderForm);
        flowService.completeTask(taskId,user);
    }
	//财务核对
	public void financialReconciliation(String taskId,int totalPrice,String wcomment,Admin user) throws BaseErrorModel{
        checkOrderState(taskId);
        Integer productRecordId=(Integer)flowService.getContentMap(taskId, "productRecordId");
		ProductRecord dbProductRecord=this.loadById(productRecordId);
		dbProductRecord.setTotalPrice(totalPrice);
		dbProductRecord.setFinancer(user);
		dbProductRecord.setWcomment(wcomment);
		this.update(dbProductRecord);
        OrderForm orderForm = dbProductRecord.getOrderForm();
        orderForm.setMcomment(wcomment);
        orderFormDAO.update(orderForm);
        flowService.completeTask(taskId,user);
    }

	

	/**
	 * 完成过磅任务
	 * @param taskId 任务号
	 * @param wcomment 备注
	 * @throws BaseErrorModel
	 */
	public void weight(String taskId,List<ProductTrace> traces,String wcomment,Admin user) throws BaseErrorModel {
        checkOrderState(taskId);
        Integer orderFormId = (Integer) flowService.getContentMap(taskId,"orderFormId");
		Integer productRecordId = (Integer) flowService.getContentMap(taskId, "productRecordId");
		OrderForm orderForm=orderFormDAO.getById(orderFormId);
		ProductRecord productRecord=this.getById(productRecordId);
		productRecord.setWcomment(wcomment);
        orderForm.setMcomment(wcomment);
        boolean ofcomplete = updateProductDetail(traces,productRecord);
		productRecord.setWeighter(user);//记录当前操作用户为过磅员
		this.getDao().update(productRecord);
        List<String> squence=orderForm.generateStepList();
        String nextStep = squence.get(0);
		Map map=new HashMap();
		flowService.completeTask(taskId, nextStep,map,user);
	}

    private boolean updateProductDetail(List<ProductTrace> traces,ProductRecord productRecord){
        OrderForm orderForm=productRecord.getOrderForm();
        this.getDao().refresh(orderForm);
        boolean ofcomplete = true;
        for (ProductTrace pt : traces) {
            OrderDetail od = orderDetailDAO.loadById(pt.getDbId());
            ProductRecordDetail detail = productRecordDetailDAO.getByOrderDetail(od,productRecord);
            detail=detail==null? new ProductRecordDetail():detail;
            detail.setOrderDetail(orderDetailDAO.loadById(pt.getDbId()));
            detail.setProductRecord(productRecord);
            detail.setDetailWeight(pt.getFinishWeight());
            detail.setDetailQuantity(pt.getFinishQuantiy());
            od.setCompWeight(od.getCompWeight()+detail.getDetailWeight());
            od.setCompQuantity(od.getCompQuantity()+detail.getDetailQuantity());
            int compWeight =  orderForm.getCompWeight()==null?0:orderForm.getCompWeight();
            orderForm.setCompWeight(compWeight+detail.getDetailWeight());
            int compQuantity =  orderForm.getCompQuantity()==null?0:orderForm.getCompQuantity();
            orderForm.setCompQuantity(compQuantity+detail.getDetailQuantity());
            od.setCompStatus(pt.getEnough());
            ofcomplete=ofcomplete&pt.getEnough()!=(byte)0;
            orderDetailDAO.update(od);
            productRecordDetailDAO.update(detail);
        }
        orderForm.setCompStatus(ofcomplete?(byte)1:(byte)0);
        //修改订单
        orderFormDAO.update(orderForm);
        return ofcomplete;
    }

	/**
	 * 删除生产记录的同时，更新订单以及订单明细
	 */
	public void delete(ProductRecord productRecord) throws BaseErrorModel{
        flowService.deleteProcessInstanceCascade("productRecordFlow."+productRecord.getCode());
        this.getDao().delete(productRecord);
	}
	/**
	 * 删除正在执行的跟踪单流程
	 * @param id
	 * @throws BaseErrorModel
	 */
	public void deleteProductFlowInstanceCascade(String id) throws BaseErrorModel{
		Execution execution=flowService.getExecutionService().findExecutionById(id);
		Integer productRecordId=(Integer) flowService.getExecutionService().getVariable(id, "productRecordId");
		if(productRecordId!=null){
		ProductRecord productRecord=this.getById(productRecordId);
		if(productRecord!=null){
			this.delete(productRecord);
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
		Integer orderFormId = (Integer) flowService.getContentMap(taskId,"orderFormId");
		Integer productRecordId = (Integer) flowService.getContentMap(taskId, "productRecordId");
		OrderForm orderForm=orderFormDAO.getById(orderFormId);
		ProductRecord productRecord=this.getById(productRecordId);
		Map map=new HashMap();
		map.put("orderForm", orderForm);
		map.put("productTeamId", flowService.getContentMap(taskId, "productTeamId"));
		map.put("productNum", flowService.getContentMap(taskId, "productNum"));
		map.put("productCode", flowService.getContentMap(taskId, "productCode"));
		map.put("productRecord", productRecord);
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
	
}
