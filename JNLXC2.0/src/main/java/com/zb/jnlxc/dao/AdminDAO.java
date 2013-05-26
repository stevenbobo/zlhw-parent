package com.zb.jnlxc.dao;

import java.util.List;

import com.ZLHW.base.Exception.BaseErrorModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.IDAO.IAdmin;
import com.zb.jnlxc.model.Admin;

@Component
public class AdminDAO extends DAO<Admin,Integer> implements IAdmin{
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

    public List<Admin> findAdminsByAuthorName(String name){
        String hql = "select distinct(au.admin) from AdminUserGroup au,UserGroup_Author ua where " +
                "au.userGroup=ua.userGroup and ua.authorDetail.name=?";
        return findByHQL(hql,name);
    }

    public List<Admin> findAdminsByAuthorId(Integer dbId){
        String hql = "select distinct(au.admin) from AdminUserGroup au,UserGroup_Author ua where " +
                "au.userGroup=ua.userGroup and ua.authorDetail.dbId=?";
        return findByHQL(hql,dbId);
    }

    public List<Admin> findAdminsByGroupName(String name){
        String hql = "select distinct(au.admin) from AdminUserGroup au " +
                "where au.userGroup.name=?";
        return findByHQL(hql,name);
    }

    public List<Admin> findAdminsByGroupId(Integer dbId){
        String hql = "select distinct(au.admin) from AdminUserGroup au " +
                "where au.userGroup.dbId=?";
        return findByHQL(hql,dbId);
    }

    public List<Admin> findAllAgent(){
        return  findAdminsByGroupName("业务员");
	}
	
	public List<Admin> findAllSuper(){
        return  findAdminsByGroupName("超级管理员");
	}
	
	public List<Admin> findAllRepairer(){
        return  findAdminsByGroupName("修模工");
	}
	
	public List<Admin> findAllWaixie(){
        return  findAdminsByGroupName("外协");
	}
	
	public List<Admin> findAllJiTai(){
        return  findAdminsByGroupName("机台");
	}
	
	

}
