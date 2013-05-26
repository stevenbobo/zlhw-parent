package com.zb.jnlxc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ZLHW.base.dao.QueryCondition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.model.Client;
import com.zb.util.StringUtils;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.springframework.stereotype.Service;

import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.WorkFlowType;
@Service
public class TaskHistoryService extends BaseService{
	
	/**
	 * 获取用户指定类型的任务记录
	 * @param workFlow
	 * @param admin
     * @Param user 当前用户
	 * @return
	 */
	public List<HistoryTask> getTaskHistory(String workFlow,Admin admin,Admin user){
		String HQL = "";
		if(admin == null)
			HQL += "from HistoryTaskImpl ht where ht.assignee = '"+user.getAccount()+"'";
		else
			HQL += "from HistoryTaskImpl ht where ht.assignee = '"+admin.getAccount()+"' ";
		
		WorkFlowType workFlowType = WorkFlowType.converseToEnum(workFlow);
		
		HQL+="and ht.processInstanceId like '%"+workFlowType.getValue()+"%'";
		
		return this.getDao().findByHQL(HQL);
	}
	
	/**
	 * 获取任务记录链
	 * @return
	 */
	public List<HistoryTask> getTaskHistoryChain(String processInstanceId){
		String HQL = "from HistoryTaskImpl ht where ht.executionId like '%"+processInstanceId+"'%"; 
		return this.getDao().findByHQL(HQL);
	}

	/**
	 * 获取用户指定类型的流程实例
	 * @param workFlow
	 * @param admin
	 * @return
	 */
	public List<HistoryProcessInstance> getProcessInstance(String workFlow,Admin admin,Admin user){
		String HQL = "";
		if(admin == null)
			HQL += "select distinct(ht.processInstanceId) from HistoryTaskImpl ht where ht.assignee = '"+user.getAccount()+"'";
		else
			HQL += "select distinct(ht.processInstanceId) from HistoryTaskImpl ht where ht.assignee = '"+admin.getAccount()+"' ";
		
		WorkFlowType workFlowType = WorkFlowType.converseToEnum(workFlow);
		
		HQL+="and ht.processInstanceId like '%"+workFlowType.getValue()+"%'";
		
		HQL = "from HistoryProcessInstanceImpl hp where hp.dbid in ("+HQL+")"; 
		return this.getDao().findByHQL(HQL);
	}
	/**
	 * 获取用户的所用流程实例
	 * @param admin
	 * @return
	 */
	public List<HistoryProcessInstance> getAllProcessInstance(Admin admin,Admin user){
		String HQL = "";
		if(admin == null)
			HQL += "select distinct(ht.processInstanceId) from HistoryTaskImpl ht where ht.assignee = '"+user.getAccount()+"'";
		else
			HQL += "select distinct(ht.processInstanceId) from HistoryTaskImpl ht where ht.assignee = '"+admin.getAccount()+"' ";
		
		HQL = "from HistoryProcessInstanceImpl hp where hp.dbid in ("+HQL+")"; 
		return this.getDao().findByHQL(HQL);
	}
	/**
	 * 分页获取的流程实例
	 * @return
	 */
	public  Page getProcessInstanceByPage(Page page,Admin user){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<QueryCondition> queryConditions=new ArrayList();

        Map<String,String> map =new  HashMap<String,String>();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(page.getParameter("data")))
            map =  new Gson().fromJson(page.getParameter("data"),new TypeToken<Map<String,String>>(){}.getType());

        String hql = "select new Map(ha.historyProcessInstance.dbid as dbId,ha.historyProcessInstance.processInstanceId as processInstanceId," +
                "DATE_FORMAT(ha.historyProcessInstance.startTime,'%Y-%m-%d') as startTime," +
                "DATE_FORMAT(ha.historyProcessInstance.endTime,'%Y-%m-%d') as endTime," +
                "ha.historyProcessInstance.state as state) " +
                "from org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl ha "+
                "where  1=1";
		if(StringUtils.isNotEmpty(map.get("keyWords"))){
			hql += "and ha.historyProcessInstance.key like :keyWords";
            queryConditions.add(new QueryCondition("keyWords", map.get("keyWords")+"%"));
        }
		
		if(map.get("flowOwner").equals("1")){
			hql += "and ha.historyTask.assignee = :assignee ";
            queryConditions.add(new QueryCondition("assignee", user.getAccount()));
        }
		
		if(StringUtils.isNotEmpty(map.get("workFlowType"))){
			hql+=" and ha.historyProcessInstance.processInstanceId like :workFlowType";
            queryConditions.add(new QueryCondition("workFlowType", map.get("workFlowType")+"%"));
        }
		
		if(StringUtils.isNotEmpty(map.get("state"))){
			hql+=" and ha.historyProcessInstance.state = :state ";
            queryConditions.add(new QueryCondition("state", map.get("state")));

        }
		
        if(StringUtils.isNotEmpty(map.get("startDate"))) {
            hql+=" and ha.historyProcessInstance.startTime >= :startDate ";
            try {
                queryConditions.add(new QueryCondition("startDate", sdf.parse(map.get("startDate"))));
            } catch (ParseException e) {
                logger.error("日期解析错误");
            }
        }

        if(StringUtils.isNotEmpty(map.get("endDate"))) {
            hql+=" and ha.historyProcessInstance.startTime <= :endDate ";
            try {
                queryConditions.add(new QueryCondition("endDate", sdf.parse(map.get("endDate"))));
            } catch (ParseException e) {
                logger.error("日期解析错误");            }
        }
        hql+=" group by ha.historyProcessInstance ";
        logger.info("hql={}",hql);
		return this.getDao().findByPageWithTmpHQL(page, hql,queryConditions);
	}
	public List<Object> getTaskHistoryDetail(Long processInstanceId){
		return this.getDao().findByHQL("select new Map(ha.dbid as dbId,ha.activityName as activityName," +
                "DATE_FORMAT(ha.startTime,'%Y-%m-%d') as startTime," +
                "DATE_FORMAT(ha.endTime,'%Y-%m-%d')  as endTime," +
                "ha.historyTask.assignee as assignee," +
                "ha.historyTask.outcome as outcome) " +
                "from org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl ha " +
                "where ha.historyProcessInstance.dbid = ?", processInstanceId);
	}
	
}
