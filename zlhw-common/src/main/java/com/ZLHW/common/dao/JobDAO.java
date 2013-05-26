package com.ZLHW.common.dao;

import java.util.List;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.common.model.Job;
@Repository
public class JobDAO extends DAO<Job,Integer> {
	private static final Log log = LogFactory.getLog(JobDAO.class);
	/**
	 * 查找某机构的所有岗位
	 * @param organId
	 * @return
	 */
	public List<Job> findAllByOrgan(Integer organId){
		return this.findByHQL("select t.job from Organ_Job t where t.organ.dbId=?", organId);
	}

	/**
	 * 删除岗位的同时删除角色-岗位、机构-岗位的相关数据
	 */
	public void delete(Job entity) throws BaseErrorModel{
		this.updateHql("delete from Job_Role t where t.job=?", entity);
//		this.updateHql("upate Admin t set t.organ_Job=null where t.organ_Job.job=?", entity);
		this.updateHql("delete from Organ_Job t where t.job=?", entity);
	}
}
