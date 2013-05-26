package com.ZLHW.common.dao;

import java.util.List;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.common.model.Job_Role;
import com.ZLHW.common.model.Organ_Job;
@Repository
public class Job_RoleDAO extends DAO<Job_Role,Integer> {
	private static final Log log = LogFactory.getLog(Job_RoleDAO.class);
	@Override
	public Job_Role create(Job_Role entity) throws BaseErrorModel {
		String hql=" from Job_Role t where t.role = ? and t.job = ?";
		List l=this.findByHQL(hql, entity.getRole(),entity.getJob());
		if(l.size()!=0){
			throw new BaseErrorModel("该岗位已有此角色", "");
		}
		return super.create(entity);
	}
}
