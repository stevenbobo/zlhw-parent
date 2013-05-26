package com.ZLHW.common.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.Job_RoleDAO;
import com.ZLHW.common.model.Job;
import com.ZLHW.common.model.Job_Role;
import com.ZLHW.common.model.Role;
@Transactional
@Service("Job_RoleService")
public class Job_RoleService extends BaseService<Job_RoleDAO,Job_Role,Integer> {
	private static final Log log = LogFactory.getLog(Job_RoleService.class);
	public void editRoles(Job job,List<Role> roles) throws BaseErrorModel{
		//查询被删的岗位
		String hql1="from Job_Role t where t.job= ? and t.role not in(?)";
		List<Job_Role> l1=this.getDao().findByHQL(hql1, job,roles);
		for(Job_Role job_Role:l1){
			this.delete(job_Role);
		}
		//查询不变的岗位
		String hql2="from Job_Role t where t.job= ? and t.role in(?)";
		List<Job_Role> l2=this.getDao().findByHQL(hql2, job,roles);
		for(Role role:roles){
			boolean newOne=true;
			for(Job_Role job_Role:l2){
				//如果不在表中则增加
				if(role.getDbId().equals(job_Role.getRole().getDbId())){
					newOne=false;
					break;
				}
			}
			if(newOne){
				Job_Role oj=new Job_Role();
				oj.setJob(job);
				oj.setRole(role);
			}
		}
	}
}
