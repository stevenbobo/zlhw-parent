package com.zb.jnlxc.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ZLHW.base.dao.QueryCondition;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.AdminDAO;
import com.zb.jnlxc.dao.ClientDAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Client;
@Transactional
@Service
public class ClientService extends BaseService<ClientDAO,Client, Integer> {
	private static final Log log = LogFactory.getLog(ClientService.class);
	@Resource
	private AdminService adminService;

	public Page loadClientByAgent(Page page,Admin user){
        StringBuffer hql = new StringBuffer("from Client t where 1=1 ");
        List<QueryCondition> queryConditions=new ArrayList();
        if(!adminService.isSuperAgent(user)){
            hql.append("and t.agent=:RefAgent ");
            queryConditions.add(new QueryCondition("RefAgent", user));
        }

        if(StringUtils.isNotEmpty(page.getParameter("name"))){
            String clentName = page.getParameter("name");
            hql.append("and (t.name like :clientName or clientCode like:clientName) ");
            queryConditions.add(new QueryCondition("clientName", "%"+clentName+"%"));
        }

        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        return this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
    }



	/**
	 * 添加一个客户，处理客户编号之后添加
	 * @throws BaseErrorModel 
	 */
	public Client addClient(Client client) throws BaseErrorModel{

		Admin agent=adminService.getById(client.getAgent().getDbId());
		int clientNum=agent.getNextClientNum();
		agent.setNextClientNum(clientNum+1);
        adminService.update(agent);
        String agentId=String.format("%1$04d", client.getAgent().getDbId());
        String clientCode = String.format("%1$04d", clientNum);
		client.setNextOrderFormNum(1);
		client.setClientCode(agentId+clientCode);
		this.getDao().create(client);
		return client;
	}

    /**
     * 更新一个客户，处理客户编号之后添加
     * @throws BaseErrorModel
     */
    public void update(Client client) throws BaseErrorModel{
        Client originalclient = getById(client.getDbId());
        //判断业务员是否发生改变
        if(client.getAgent()!=null)
        if(originalclient.getAgent()==null||
                !originalclient.getAgent().getDbId().equals(client.getAgent().getDbId())){
            Admin agent=adminService.getById(client.getAgent().getDbId());
            int clientNum=agent.getNextClientNum();
            agent.setNextClientNum(clientNum+1);
            adminService.update(agent);
            String agentId=String.format("%1$04d", client.getAgent().getDbId());
            String clientCode = String.format("%1$04d", clientNum);
            client.setClientCode(agentId+clientCode);
        }
        this.getDao().merger(client);
    }

//    public void updateForOld(Client client) throws BaseErrorModel{
//                Admin agent=adminService.getById(client.getAgent().getDbId());
//                int clientNum=agent.getNextClientNum();
//                agent.setNextClientNum(clientNum+1);
//                adminService.update(agent);
//                String agentId=String.format("%1$04d", client.getAgent().getDbId());
//                String clientCode = String.format("%1$04d", clientNum);
//                client.setClientCode(agentId+clientCode);
//        this.getDao().merger(client);
//    }

}
