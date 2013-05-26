package com.ZLHW.common.dao;

import java.util.List;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.common.model.AuthorDetail;
@Repository
public class AuthorDetailDAO extends DAO<AuthorDetail,Integer> {
	private static final Log log = LogFactory.getLog(AuthorDetailDAO.class);
	/** 获取默认权限 */
	public List<AuthorDetail> getDefaultAuthor(){
		return findByHQL("from AuthorDetail t where t.id like '8%' ");
	}
	/**
	 * 删除权限明细的同时删除Role_AuthorDetail表中相关记录
	 */
	public void delete(AuthorDetail entity) throws BaseErrorModel{
		this.updateHql(" delete from Role_AuthorDetail t where t.authorDetail=?", entity);
		super.delete(entity);
	}
}
