package com.zb.jnlxc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.OrderDetailForm;
import com.zb.jnlxc.model.*;
import com.zb.util.Constants;
import com.zb.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;

@Service
@Transactional
public class BusinessService extends BaseService<OrderFormDAO,OrderForm, Integer>{
	private static final Log log = LogFactory.getLog(BusinessService.class);
    @Resource
    private PaichanOrderDetailDAO paichanOrderDetailDAO;
	@Resource
	private OrderDetailDAO orderDetailDao;
	@Resource
	private AdminDAO adminDao;
    @Resource
    private AdminService adminService;
	@Resource
	private OrderForm_AuthorDAO orderForm_AuthorDAO;
	@Resource
	private ClientDAO clientDAO;
    @Resource
    private PaiChanRecordDAO paiChanRecordDAO;
	@Resource
	private FlowService flowService;
    @Resource
    private DataDictService dataDictService;
    @Resource
    private CityService cityService;
    @Resource
    private PaichanRecordService paichanRecordService;
    @Resource
    private ProductTeamDAO productTeamDAO;
    @Resource
    private RemaindProductDAO remaindProductDAO;

	private List<Admin> combinClient(List<Admin> agentList,List<Client> clientList){
		List<Admin> emptyAgentList = new ArrayList<Admin>();
		for(Admin agent : agentList){
			List<Client> clientArray = new ArrayList<Client>();
			for(Client client : clientList){
				if(client.getAgent()!=null 
						&& agent.getId().equals(client.getAgent().getDbId())){
					clientArray.add(client);
				}
			}
			if(clientArray.size()==0)
				emptyAgentList.add(agent);
//			else
//				agent.setChildren(clientArray);
		}
		agentList.removeAll(emptyAgentList);
		return agentList;
	}

	public List<OrderForm> getOrderFormsByScheme(Integer schemeId) throws BaseErrorModel{
        return this.getDao().findByHQL("from OrderForm o where o.scheme.id=?", schemeId);
	}
	
//	public List<ProductRecord> getProductRecordsByScheme(Integer schemeId){
//		return productRecordDAO.getProductRecordsByScheme(schemeId);
//	}

    public Page loadOrderDetail(MiniPageReq page, Admin user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from OrderDetail t where 1=1 ");
        hql.append(" and compStatus = 0 ");
        if(StringUtils.isNotEmpty(page.getParameter("remaindProductId"))){
            int remaindProductId=Integer.parseInt(page.getParameter("remaindProductId"));
            RemaindProduct remaindProduct = remaindProductDAO.getById(remaindProductId);
            Scheme scheme = remaindProduct.getPaichanOrderDetail().getOrderDetail().getOrderForm().getScheme();
            String setSize = remaindProduct.getPaichanOrderDetail().getOrderDetail().getSetSize();
            hql.append("and t.setSize=:RefSetSize ");
            queryConditions.add(new QueryCondition("RefSetSize", setSize));
            hql.append("and t.orderForm.scheme=:RefScheme ");
            queryConditions.add(new QueryCondition("RefScheme", scheme));
        }
        if(StringUtils.isNotEmpty(page.getParameter("code"))){
            String code =  page.getParameter("code");
            hql.append("and t.orderFrom.code like :code ");
            queryConditions.add(new QueryCondition("code", "%"+code+"%"));
        }
        hql.append("order by dbId desc ");
        this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

	/**
	 * 通过分页载入订单
	 * @param page
     * @param user 当前的用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page loadOrderForm(Page page,Admin user) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<QueryCondition> queryConditions=new ArrayList();
		StringBuffer hql=new StringBuffer("from OrderForm t where 1=1 ");
		if(StringUtils.isNotEmpty(page.getParameter("clientId"))){
			int clientId=Integer.parseInt(page.getParameter("clientId"));
			hql.append("and t.client.dbId=:RefClient ");
			queryConditions.add(new QueryCondition("RefClient", clientId));
		}
        if(StringUtils.isNotEmpty(page.getParameter("schemeId"))){
            int schemeId=Integer.parseInt(page.getParameter("schemeId"));
            hql.append("and t.scheme.dbId=:RefSchemeId ");
            queryConditions.add(new QueryCondition("RefSchemeId", schemeId));
        }
        if(!adminService.isSuperAgent(user)){
            hql.append("and t.client.agent.dbId=:RefAgent ");
            queryConditions.add(new QueryCondition("RefAgent", user.getDbId()));
        }
        if(StringUtils.isNotEmpty(page.getParameter("startDate"))){
            hql.append("and t.orderDate >=:startDate ");
            Date startDate=sdf.parse(page.getParameter("startDate"));
            queryConditions.add(new QueryCondition("startDate", startDate));
        }
        if(StringUtils.isNotEmpty(page.getParameter("endDate"))){
            hql.append("and t.orderDate <=:endDate ");
            Date endDate=sdf.parse(page.getParameter("endDate"));
            queryConditions.add(new QueryCondition("endDate", endDate));
        }
        if(StringUtils.isNotEmpty(page.getParameter("code"))){
            String code =  page.getParameter("code");
			hql.append("and t.code like :code ");
            queryConditions.add(new QueryCondition("code", "%"+code+"%"));
		}
        if(StringUtils.isNotEmpty(page.getParameter("cityId"))){
            Integer cityId =  Integer.parseInt(page.getParameter("cityId"));
            String cityCode=cityService.getCode(cityService.getById(cityId));
            hql.append("and t.city.code like :cityCode ");
            queryConditions.add(new QueryCondition("cityCode", cityCode+"%"));
        }
        if(StringUtils.isNotEmpty(page.getParameter("compStatus"))){
            Byte compStatus = Byte.parseByte(page.getParameter("compStatus"));
            hql.append("and t.compStatus=:compStatus ");
            queryConditions.add(new QueryCondition("compStatus", compStatus));
        }
        if(StringUtils.isNotEmpty(page.getParameter("paichan"))){
            hql.append("and t.currentState=:currentState ");
            queryConditions.add(new QueryCondition("currentState", Constants.ORDER_IN_WAITING_PAICHAN));
        }
        if(StringUtils.isNotBlank(page.getParameter("paiChanRecordId"))){
            Integer paiChanRecordId = Integer.parseInt(page.getParameter("paiChanRecordId"));
            PaiChanRecord paiChanRecord = paiChanRecordDAO.getById(paiChanRecordId);
            OrderForm orderForm = paiChanRecord.getOrderForm();
            if(orderForm!=null)
                hql.append("and t.dbId = ").append(orderForm.getDbId());
        }
        if(StringUtils.isNotEmpty(page.getParameter(" orderType"))){
            int orderType = Integer.parseInt(page.getParameter("orderType"));
//        {id:0,text:'下单时间'}, {id:1, text: '交货期剩余天数'},{id:2,text:'订单重量'}, {id:3, text: '订单数量'}
            switch (orderType){
                case 0:
                    hql.append("order by ").append("orderDate").append(" ").append(page.getSortOrder()); break;
                case 1:
                    hql.append("order by ").append("deadline").append(" ").append(page.getSortOrder());break;
                case 2:
                    hql.append("order by ").append("totalWeight").append(" ").append(page.getSortOrder());break;
                case 3:
                    hql.append("order by ").append("totalWeight").append(" ").append(page.getSortOrder());break;
                default:
                    hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
            }
        }else {
            hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        }
		this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
		return page;
	}
	public Page getOrderFormsByAgent(Page page){
		this.findByPageWithEqCondition(page);
		return page;
	}

    public List<OrderDetail> getOrderDetail(Integer orderId){
        if(orderId==null) return new ArrayList<OrderDetail>();
        OrderForm orderForm = loadById(orderId);
        return orderDetailDao.getByOrderForm(orderForm);
    }

	public Page getCompleteOrderForms(Page page){
		if(page.getQueryCondition()!=null){
			Set<String> keyset = page.getQueryCondition().keySet();
			List<Criterion> args = new ArrayList<Criterion>();
			for(String key : keyset){
				if("0".equals(page.getQueryCondition().get(key).toString())||"".equals(page.getQueryCondition().get(key).toString()))
				args.add(Expression.eq(key, page.getQueryCondition().get(key)));
			}
			this.findByPageWithExpression(page, args);
		}
		return page;
	}

    /**
     * 根据图纸编号查找
     * @param schemeId
     * @return
     */
    public List<OrderDetail> getOrderDetailByScheme(Integer schemeId){
          return orderDetailDao.getByScheme(schemeId);
    }
	

	
	public List loadAgents(){
		List<Admin> agents = adminDao.findAllAgent();
		List<Client> clients = clientDAO.findAll();
		for(Admin agent : agents){
			List children = new ArrayList();
			for(Client client : clients){
				if(client.getAgent() == agent)
					children.add(client);
			}
//			agent.setChildren(children);
		}
		return agents;
	}
	public List getAllAgents(){
		List<Admin> agents = adminDao.findAllAgent();
		List<Admin> supers = adminDao.findAllSuper();
//		if("超级管理员".equals(this.getAdmin().getUserGroup().getName())){
//			agents.add(0, this.getAdmin());
//		}
		//将所有管理员与业务员作为返回
		List<Admin> allUser = new ArrayList();
		allUser.addAll(supers);
		allUser.addAll(agents);
//		for(Admin agent : allUser)
//			agent.setChildren(clientDAO.findByHQLWithIndex("from Client t where t.agent=?", agent));
		return allUser;
	}
	
//	/**
//	 * 通过订单得到生产单
//	 * @param orderForm
//	 * @return
//	 */
//	public List getProductRecordsByOrderForm(OrderForm orderForm){
//		return productRecordDAO.findByHQL("from ProductRecord p where p.orderForm=?", orderForm);
//	}
//
//	/**
//	 * 通过生产单得到订单详细
//	 * @param productRecord
//	 * @return
//	 */
//	public List getOrderDetailsByProductRecord(ProductRecord productRecord){
//		List<ProductRecordDetail> list=productRecordDetailDAO.findByHQL("from ProductRecordDetail p where p.productRecord=?", productRecord);
//		List OrderDetails=new ArrayList();
//		for(ProductRecordDetail prd:list){
//			OrderDetails.add(prd.getOrderDetail());
//		}
//		return OrderDetails;
//	}
//
	/**
	 * 保存已授权的业务员列表到OrderForm_Author表中
	 * @throws BaseErrorModel 
	 */
	public void saveAuthorizedAgents(List<Admin> authorizedAgents,Admin user) throws BaseErrorModel{
		Admin loginAdmin=user;
		List<OrderForm_Author> oldAuthorizedAgents=orderForm_AuthorDAO.findByHQL("from OrderForm_Author ofa where ofa.ownerAdmin=?", user);
		for(OrderForm_Author orderForm_Author:oldAuthorizedAgents){
			orderForm_AuthorDAO.delete(orderForm_Author);
		}
		for(Admin agent:authorizedAgents){
			OrderForm_Author orderForm_Author=new OrderForm_Author();
			orderForm_Author.setOwnerAdmin(loginAdmin);
			orderForm_Author.setAuthorizedAdmin(agent);
			orderForm_AuthorDAO.create(orderForm_Author);
		}
	}
	
	/**
	 * 初始化界面
	 * @return
	 */
	public Map initOrderProxy(){
		Map map = new HashMap();
        dataDictService.loadDictList("materia");
		map.put("materialArray", dataDictService.loadDictList("materia"));
		map.put("surfaceProcessArray", dataDictService.loadDictList("surfaceProcess"));
		map.put("packArray", dataDictService.loadDictList("pack"));
		return map;
	}
	/**
	 * 再次查询客户中下张订单num
	 * @return
	 */
	public String generateCode(Integer clientId) {
        Client client = clientDAO.getById(clientId);
		//查询客户中下个订单num
		int orderNum =client.getNextOrderFormNum();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		//通过客户编号+客户订单数量 生成订单编号
		String orderCode = client.getClientCode()+"-"+date+"-"+String.format("%04d",orderNum);
		return orderCode;
	}
	/**
	 * 保存订单，开启流程
	 * @param orderForm
	 * @return
	 * @throws BaseErrorModel
	 */
	public OrderForm saveOrder(OrderForm orderForm,List<OrderDetailForm> orderDetails,Admin user) throws BaseErrorModel{
		Map map = new HashMap();
		orderForm.setKaidan(user);
		orderForm.setCompWeight(0);
        orderForm.setCompQuantity(0);
		orderForm.setOrderDate(new Date());
		orderForm.setEnable((byte)0);
		orderForm.setCurrentState(Constants.ORDER_IN_ORDERFLOW);
		orderForm.setNextRecordNum(1);
		orderForm.setCompStatus((byte)0);
        this.getDao().getSession().refresh(orderForm.getClient());
        this.getDao().getSession().refresh(orderForm.getScheme());
		this.create(orderForm);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String now =sdf.format( new Date());
//		更新客户中的下一个订单号
		Client client = orderForm.getClient();
        Date lastOrderDate = client.getLastOrderDate();
        int num = client.getNextOrderFormNum();
        num =lastOrderDate!=null&&(now.equals(sdf.format(lastOrderDate)))?num+1:1;
        client.setLastOrderDate(new Date());
		client.setNextOrderFormNum(num);
		clientDAO.update(client);


//		//添加订单详细，统计订单重量
		int weight=0;
		for(OrderDetailForm orderDetailForm : orderDetails){
            if("added".equals(orderDetailForm.get_state())){
                orderDetailForm.setOrderForm(orderForm);
                orderDetailForm.setCompQuantity(0);
                orderDetailForm.setCompWeight(0);
                orderDetailForm.setPaichanQuantity(0);
                orderDetailForm.setPaichanWeight(0);
                orderDetailDao.create(orderDetailForm.getOrderDetail());
                weight+=orderDetailForm.getOrderWeight();
            }
		}
		orderForm.setTotalWeight(weight);
		this.update(orderForm);
		map.put("orderFormId", orderForm.getDbId());
		map.put("weight", weight);
		map.put("agent", adminDao.getById(orderForm.getClient().getAgent().getDbId()).getAccount());
		map.put("kaidan", orderForm.getKaidan().getAccount());
		map.put("assigner", user.getAccount());
        map.put("shixiao",orderForm.getShixiao());
		flowService.startProcessInstanceByKey("orderFlow", orderForm.getCode(), map);
		return orderForm;
	}
	
	/**
	 * 更新订单
	 * @throws BaseErrorModel 
	 */
	public OrderForm updateOrder(OrderForm orderForm,List<OrderDetailForm> orderDetails) throws BaseErrorModel{
		//如果处于流程间断则无法编辑
		if(orderForm.getCurrentState()==(byte)1){
			throw new BaseErrorModel("该订单目前处于流程间断，无法编辑", "");
		}
        if(orderForm.getCurrentState()==(byte)2){
            throw new BaseErrorModel("该订单被暂停，无法编辑", "");
        }
		return updateOrderInFlow(orderForm,orderDetails);
	}
	
	/**
	 * 流程里的更新订单
	 * @throws BaseErrorModel 
	 */
	public OrderForm updateOrderInFlow(OrderForm orderForm,List<OrderDetailForm> orderDetails) throws BaseErrorModel{
        getDao().getSession().refresh(orderForm.getClient());
        getDao().getSession().refresh(orderForm.getScheme());
		int weight=0;
		for(OrderDetailForm orderDetailForm : orderDetails){
            String state = orderDetailForm.get_state();
            orderDetailForm.setOrderForm(orderForm);
            if("removed".equals(state)){
                orderDetailDao.delete(orderDetailForm.getOrderDetail());
            }else if("added".equals(state)){
                weight+=orderDetailForm.getOrderWeight();
                orderDetailForm.setCompQuantity(0);
                orderDetailForm.setCompWeight(0);
                orderDetailDao.create(orderDetailForm.getOrderDetail());
            }else if ("modified".equals(state)){
                weight+=orderDetailForm.getOrderWeight();
                orderDetailDao.update(orderDetailForm.getOrderDetail());
            } else {}
		}
		orderForm.setTotalWeight(weight);
		this.update(orderForm);
        return orderForm;
	}
	
	/**
	 * 通过login admin得到订单查看权限OrderForm_Author的admin集合
	 */
	public List<Admin> getOrderFormAuthor(Admin loginAgent){
		List<OrderForm_Author> orderForm_AuthorList=orderForm_AuthorDAO.findByHQL("from OrderForm_Author oa where oa.ownerAdmin=?", loginAgent);
		List<Admin> authorizedAgents=new ArrayList<Admin>();
		for(OrderForm_Author orderForm_Author:orderForm_AuthorList){
			authorizedAgents.add(orderForm_Author.getAuthorizedAdmin());
		}
		return authorizedAgents;
	}

    /**
     * 检查流程是否是暂停状态
     * @param orderForm
     */
    public void checkOrderState(OrderForm orderForm){
          if(orderForm.getCurrentState()==2)
              throw new BaseErrorModel("流程暂停，无法操作", "");
    }
	
	
	/**
	 * 开单员更新订单
	 * @throws BaseErrorModel 
	 */
	public OrderForm completeUpdateOrder(String taskId,OrderForm orderForm,List<OrderDetailForm> orderDetails,Admin user){
        checkOrderState(orderForm);
        clientDAO.refresh(orderForm.getClient());
		String agentAccount = adminDao.getById(orderForm.getClient().getAgent().getDbId()).getAccount();
		Map map = new HashMap();
		this.updateOrderInFlow(orderForm,orderDetails);
		map.put("orderForm", orderForm);
        map.put("agent", agentAccount);
		String lastTaskName=(String) flowService.getContentMap(taskId, "lastTaskName");
		if(lastTaskName.equals("agentAudit")){
			flowService.completeTask(taskId,"to 业务员核对",map,user);
		}else{
			flowService.completeTask(taskId,"to 生产部审核",map,user);
		}
        return orderForm;
	}
	
	public void deleteProcessInstanceCascade(String executionId) throws BaseErrorModel{
		Integer orderFormId=(Integer) flowService.getContentMapByExecution(executionId, "orderFormId");
		Integer count=this.findHql("from OrderForm t where t.id=?", orderFormId).size();
		if(count!=0){
			try {
				this.getDao().updateHql("update OrderForm t set t.currentState=? where t.id=?", (byte)0,orderFormId);
			} catch (BaseErrorModel e) {
				e.printStackTrace();
				throw new BaseErrorModel("更新currentState字段出错", "");
			}
		}
		flowService.deleteProcessInstanceCascade(executionId);
	}
	

	
	/**
	 * 生产部审核
	 * @param taskId
	 * @param success
	 * @throws BaseErrorModel 
	 */
	public void productionAudit(String taskId, boolean success, String remark,Admin user) throws BaseErrorModel {
		OrderForm of=this.getOrderFormInfo(taskId);
        checkOrderState(of);
		of.setMcomment(remark);
		if (success)
			flowService.completeTask(taskId, "通过",user);
		else{
			Map map=new HashMap();
			map.put("cancler", user.getAccount());
			map.put("lastTaskName", "productionAudit");
			flowService.completeTask(taskId, "不通过",map,user);
		}
	}
	/**
	 * 业务员核对
	 * @param taskId
	 * @param success
	 * @throws BaseErrorModel 
	 */
	public void agentAudit(String taskId, boolean success,String remark,Admin user) throws BaseErrorModel{
        OrderForm orderForm = getOrderFormInfo(taskId);
        checkOrderState(orderForm);
        orderForm.setMcomment(remark);
		this.update(orderForm);
		if (success)
			flowService.completeTask(taskId, "通过",user);
		else{
			Map map=new HashMap();
			map.put("cancler",user.getAccount());
			map.put("lastTaskName", "agentAudit");
			flowService.completeTask(taskId, "不通过",map,user);
		}
	}
	
	/**
	 * 模具主管核对
	 * @param taskId
	 * @param success
	 * @throws BaseErrorModel 
	 */
	public void moulderAudit(String taskId, boolean success,String remark,Admin user) throws BaseErrorModel{
		String moulder=user.getAccount();
		OrderForm of=this.getOrderFormInfo(taskId);
        checkOrderState(of);
		of.setMcomment(remark);
		this.update(of);
		Map map = new HashMap();
		map.put("moulder", moulder);
		if (success)
			flowService.completeTask(taskId, "有库存",map,user);
		else
			flowService.completeTask(taskId, "无库存",map,user);
	}
	
	/**
	 * 车间给订单分配指定机台
	 * @param taskId
	 * @param team
	 * @throws BaseErrorModel 
	 */
	/**
	public void workshopTask(String taskId,ProductTeam team,OrderForm orderForm) throws BaseErrorModel{
		OrderForm of=this.getById(orderForm.getDbId());
		of.setMcomment(orderForm.getMcomment());
		this.update(of);
		Map map = new HashMap();
		map.put("productTeam", team);
		this.completeTask(taskId,"合并",map);
	}
	**/
	
	/**
	 * 从流程中取得订单信息
	 * @param taskId
	 * @return
	 */
	public OrderForm getOrderFormInfo(String taskId){
		Integer orderFormId=(Integer) flowService.getContentMap(taskId, "orderFormId");
		return this.getById(orderFormId);
	}
	
	
	

	/**
	 * 发布一个订单流程图(流程版本，只发布一次，平常不用)
	 * 流程图jpdl位置（比如"./com/zb/JBPM/orderFlow.jpdl.xml"）
	 * @return
	 */
	public String deployFlow() {
		String path = "./com/zb/JBPM/orderFlow.jpdl.xml";
		return flowService.getRepositoryService().createDeployment()
				.addResourceFromClasspath(path).deploy();
	}

	/**
	 * 开启订单流程
	 * 
	 * @param id
	 *            模具订单号
	 * @return
	 */
	public ProcessInstance startOrderFlowByKey(String id) {
		return flowService.startProcessInstanceByKey("orderFlow", id);
	}

	/**
	 * 开启订单流程
	 * 
	 * @param id
	 *            模具订单号
	 * @return
	 */
	public ProcessInstance startOrderFlowByKey(String id, Map map) {
		return flowService.startProcessInstanceByKey("orderFlow", id, map);
	}

	/**
	 * 找到当前的流程实例
	 * 
	 * @param id
	 * @return
	 */
	public ProcessInstance findProcessInstanceByKey(String id) {
		return flowService.findProcessInstanceByKey("orderFlow", id);
	}
	
	public void deleteOrder(Integer orderId) throws BaseErrorModel{
        OrderForm orderForm = loadById(orderId);
        flowService.deleteProcessInstanceCascade("orderFlow."+orderForm.getCode());
        this.getDao().delete(orderForm);
	}
    /**
     * 获取某客户某图纸的最后一张订单
     * @param schemeId
     * @param clientId
     * @return
     */
    public OrderForm getLastOrderForm(Integer schemeId, Integer clientId) {
        List<OrderForm> l =this.getDao().findByHQLWithIndex("from OrderForm where scheme.dbId=? and client.dbId=? order by dbId desc", 0, 1, schemeId, clientId);
        if(l.size()>0)
            return l.get(0);
        else
            return null;
    }

    /**
     * 暂停流程
     * @param orderId
     */
    public void stopOrderFlow(Integer orderId) {
        OrderForm orderForm = loadById(orderId);
        orderForm.setCurrentState(Constants.ORDER_IN_ORDERFLOW_PAUSE);
        this.update(orderForm);

    }
    /**
     * 重启流程
     * @param orderId
     */
    public void restartOrderFlow(Integer orderId) {
        OrderForm orderForm = loadById(orderId);
        orderForm.setCurrentState(Constants.ORDER_IN_ORDERFLOW);
        this.update(orderForm);
    }



    public void addpaiChan(String orderId, String productTeamId,List<PaichanOrderDetail> paichanOrderDetailList) {
        PaiChanRecord paichanRecord = new PaiChanRecord();
        paichanRecord.setEnable((byte)1);
        if(StringUtils.isEmpty(orderId)||StringUtils.isEmpty(productTeamId)){
            throw new BaseErrorModel("参数异常","");
        }
        OrderForm orderForm = getById(Integer.parseInt(orderId));
        paichanRecord.setOrderForm(orderForm);
        paichanRecord.setOrderCodes(orderForm.getCode());
        ProductTeam productTeam = productTeamDAO.loadById(Integer.parseInt(productTeamId));
        paichanRecord.setProductTeam(productTeam);
        paichanRecord.setCreateDate(new Date());

        paichanRecord.setRecordNum(orderForm.getNextRecordNum());
        paichanRecord.setCharger(adminDao.loadById(paichanRecord.getProductTeam().getCharge_dbId()));//设置机台负责人
        paichanRecord.setCurrentState((byte)1);
        paichanRecord.setWcomment("");

        paiChanRecordDAO.create(paichanRecord);
        for(PaichanOrderDetail paichanOrderDetail:paichanOrderDetailList){
            orderDetailDao.refresh(paichanOrderDetail.getOrderDetail());
            paichanOrderDetail.setPaichanRecord(paichanRecord);
            paichanOrderDetailDAO.create(paichanOrderDetail);
            orderDetailDao.updatePaichanWeight(paichanOrderDetail.getOrderDetail());
        }
        paichanRecordService.startProductRecordFlow(paichanRecord);

    }

    public List getUnPanchanOrderDetail(Integer orderId) {
        List l1 = new ArrayList();
        List<OrderDetail> l = orderDetailDao.getByOrderForm(loadById(orderId));
        for(OrderDetail od:l){
            PaichanOrderDetail paichanOrderDetail = new PaichanOrderDetail();
            paichanOrderDetail.setCompQuantity(0);
            paichanOrderDetail.setCompWeight(0);
            paichanOrderDetail.setSetSize(od.getSetSize());
            paichanOrderDetail.setOrderQuantity(od.getOrderQuantity()-od.getPaichanQuantity());

            paichanOrderDetail.setOrderDetail(od);

            Float kpm = od.getOrderForm().getKpm();
            Integer setSize = Integer.parseInt(od.getSetSize());
            double orderWeight=paichanOrderDetail.getOrderQuantity()*((setSize/1000.0)*kpm)/1000;
            double orderTotalMeter=(paichanOrderDetail.getOrderQuantity()*setSize)/1000.0;
            paichanOrderDetail.setOrderWeight((int)orderWeight);
            paichanOrderDetail.setOrderTotalMeter((int)orderTotalMeter);


            l1.add(paichanOrderDetail);
        }
        return l1;
    }


}
