package com.ZLHW.common.dao;

import java.util.List;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ZLHW.common.model.Admin;
import com.ZLHW.common.model.Organ_Job;
@Repository
public class Organ_JobDAO extends DAO<Organ_Job,Integer> {
	private static final Log log = LogFactory.getLog(Organ_JobDAO.class);

	@Override
	public Organ_Job create(Organ_Job entity) throws BaseErrorModel {
		String hql=" from Organ_Job t where t.organ = ? and t.job = ?";
		List l=this.findByHQL(hql, entity.getOrgan(),entity.getJob());
		if(l.size()!=0){
			throw new BaseErrorModel("该机构已有此岗位", "");
		}
		return super.create(entity);
	}
	
	/**
	 * 删除Organ_Job表的同时将用户表相关记录设置成null
	 */
	public void delete(Organ_Job entity) throws BaseErrorModel{
		String hql=" upate Admin t set t.organ_Job=null where t.organ_Job = ?";
		this.updateHql(hql, entity);
		super.delete(entity);
	}
	
}
