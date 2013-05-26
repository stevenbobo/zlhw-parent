package com.zb.jnlxc.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.Client;

@Component
public class ClientDAO extends DAO<Client,Integer> {
	private static final Log log = LogFactory.getLog(ClientDAO.class);

	public ClientDAO() {
		super();
	}

	public void delete(Client client) throws BaseErrorModel{
		//删除订单前查找是否有生产记录
		int clientNum=this.getCountOfAll("select count(*) from OrderForm m where m.client=?", client);
		if(clientNum>0)
			throw new BaseErrorModel("请先删除相关订单记录","");
		
		super.delete(client);
	}
	
}
