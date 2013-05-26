package com.ZLHW.common.dao;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.common.model.Organ;
@Repository
public class OrganDAO extends DAO<Organ,Integer> {
	private static final Log log = LogFactory.getLog(OrganDAO.class);
	public Organ findByName(String name){
		return this.findByHQL(" from Admin t where t.organ_Job.organ.name=?", name).get(0);
	}
	/**
	 * 删除机构表的同时删除Organ_Job表中记录
	 */
	public void delete(Organ entity) throws BaseErrorModel{
//		this.updateHql("upate Admin t set t.organ_Job=null where t.organ_Job.organ = ?",entity);
		this.updateHql("delete from Organ_Job t where t.organ=? ", entity);
		super.delete(entity);
	}
}
