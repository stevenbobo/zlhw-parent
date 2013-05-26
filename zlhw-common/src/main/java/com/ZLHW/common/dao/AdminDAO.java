package com.ZLHW.common.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import com.ZLHW.common.model.Admin;

@Repository
public class AdminDAO extends DAO<Admin,Integer>{
	private static final Log log = LogFactory.getLog(AdminDAO.class);

	public List<Admin> getUsersByGroupName(String groupName){
		return this.findByHQL("from Admin a where a.userGroup.name=?", groupName);
	}
	
	public Admin getUserByAccount(String account) throws BaseErrorModel{
		List<Admin> admins=this.findByHQL("from Admin a where a.account=?", account);
		if(admins.size()==0)
			throw new BaseErrorModel("无此账户","");
		else
			return admins.get(0);
	}
	
	public List<Admin> findAllAgent(){
		return findByHQL("from Admin a where a.userGroup.name=?","业务员");
	}
	
	public List<Admin> findAllRepairer(){
		return findByHQL("from Admin a where a.userGroup.name=?","修模工");
	}
	
	public List<Admin> findAllWaixie(){
		return findByHQL("from Admin a where a.userGroup.name=?","外协");
	}
	
	

}
