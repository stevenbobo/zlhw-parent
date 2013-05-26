package com.ZLHW.base.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;


import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.CommonDAO;
import com.ZLHW.base.dao.IDAO;
import com.ZLHW.base.factory.BeanFactory;
import com.danga.MemCached.MemCachedClient;
@Transactional
@Service
public class BaseService<DAO extends IDAO<BEAN, PRIMARY>,BEAN, PRIMARY> implements IService<BEAN, PRIMARY> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass()) ;
	protected MemCachedClient mcc ;
	private DAO dao;
	
	public DAO getDao() {
		if(dao==null){
			try{
				Class<DAO> beanClazz = (Class<DAO>) ((ParameterizedType) this.getClass()
						.getGenericSuperclass()).getActualTypeArguments()[0];
				dao = (DAO) BeanFactory.LookUp(beanClazz);
			}catch(Exception e){
				dao = (DAO) BeanFactory.LookUp(CommonDAO.class);
			}
		}
		return dao;
	}
	private String lowerCaseFirstWord(String str){
		return str.substring(0, 1).toLowerCase()+str.substring(1);
	}
	
	public BEAN create(BEAN entity) throws BaseErrorModel  {
		return getDao().create(entity);
	}
	
	public BEAN loadById(PRIMARY id) {
		return getDao().loadById(id);
	}

	public void delete(BEAN entity) throws BaseErrorModel {
		try{
			getDao().delete(entity);	
		}catch (Exception e) {
			logger.error("删除"+entity+"异常", e);
			throw new BaseErrorModel("无法正常删除，请先删除关联数据","");
		}
		
	}

	public void deleteById(PRIMARY id) throws BaseErrorModel {
        try{
		getDao().deleteById(id);
        }catch (Exception e) {
            logger.error("删除异常", e);
            throw new BaseErrorModel("无法正常删除，请先删除关联数据","");
        }
	}
	
	public void deleteById(PRIMARY[] ids) throws BaseErrorModel {
		for(PRIMARY id:ids) {
			getDao().deleteById(id);
		}
	}
	
	
	public BEAN refresh(BEAN entity) throws BaseErrorModel{
		getDao().refresh(entity);
		return entity;
	}

	public List<BEAN> findAll() {
		return getDao().findAll();
	}
	
	public List<BEAN> findAllAsc() {
		return getDao().findAllAsc();
	}

	public List findHql(String HQL, Object... args) {
		return getDao().findByHQL(HQL, args);
	}
	
	public List findHql(String hql, int fromIdx, int fetchCount, Object... args) {
		return getDao().findByHQL(hql, fromIdx, fetchCount, args);
	}

	public List<BEAN> findList(int pageNo, int pageSize) {
		return getDao().findList(pageNo, pageSize);
	}

	public BEAN getById(PRIMARY id) {
		return (BEAN) getDao().getById(id);
	}

	public List<BEAN> getByColumn(String columnName,Object arg) {
		return  getDao().getByColumn(columnName, arg);
	}
	
	public List<BEAN> getByColumnAsc(String columnName,Object arg) {
		return  getDao().getByColumnAsc(columnName, arg);
	}
	
	public int getCountOfAll() {
		return getDao().getCountOfAll();
	}

	public int getCountOfAll(String HQL, Object... args) {
		return getDao().getCountOfAll(HQL, args);
	}

	public void update(BEAN entity) throws BaseErrorModel {
		getDao().update(entity);
	}

	public List findHqlCached(String hql, int time, int fromIdx, int fetchCount,
			Object... args) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".findHql:").append(fromIdx).append(fetchCount);
		for(Object o:args) {
			cacheKey.append(args.toString());
		}
		List l = (List) mcc.get(cacheKey.toString());
		if (l == null) {
			l = this.findAll();
			mcc.set(cacheKey.toString(), l, time);
		}
		return l;
	}

	public List<BEAN> findAllCached(int time) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".findAll");
		List l = (List) mcc.get(cacheKey.toString());
		if (l == null) {
			l = this.findAll();
			mcc.set(cacheKey.toString(), l, time);
		}
		return l;
	}

	public List<BEAN> findListCached(int time, int pageNo, int pageSize) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".findListCached");
		cacheKey.append(pageNo).append(pageSize);
		List l = (List) mcc.get(cacheKey.toString());
		if (l == null) {
			l = this.findList(pageNo, pageSize);
			mcc.set(cacheKey.toString(), l, time);
		}
		return l;
	}

	public BEAN getByIdCached(int time, PRIMARY id) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".getByIdCached:").append(id);
		BEAN bean = (BEAN) mcc.get(cacheKey.toString());
		if (bean == null) {
			bean = this.getById(id);
			mcc.set(cacheKey.toString(), bean, time);
		}
		return bean;
	}

	public int getCountOfAllCached(int time) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".getCountOfAllCached");
		Integer total = (Integer) mcc.get(cacheKey.toString());
		if (total == null) {
			total = this.getCountOfAll();
			mcc.set(cacheKey.toString(), total, time);
		}
		return total;
	}

	public int getCountOfAllCached(String HQL, int time, String... args) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".getCountOfAllCached:");
		cacheKey.append(HQL);
		for (String str : args) {
			cacheKey.append(str);
		}
		Integer total = (Integer) mcc.get(cacheKey.toString());
		if (total == null) {
			total = this.getCountOfAll(HQL, args);
			mcc.set(cacheKey.toString(), total, time);
		}
		return total;
	}

	/**
	 * 获取测试用的springbean
	 * @return
	 */
	public BaseService getTestService(){
		return (BaseService)BeanFactory.LookUp(this.getClass().getSimpleName());
	}

	public Page findByPageWithHQL(Page page,String HQL, Object...args){
		return this.getDao().findByPageWithHQL(page, HQL, args);
	}

	public Page findAllByPage(Page page) {
		return this.getDao().findAllByPage(page);
	}
	
	public Page findByPageWithEqCondition(Page page) {
		return this.getDao().findByPageWithEqCondition(page);
	}
	public Page findByPageWithLikeCondition(Page page) {
		return this.getDao().findByPageWithLikeCondition(page);
	}
	public Page findByPageWithTimeCondition(Page page) {
		return this.getDao().findByPageWithTimeCondition(page);
	}
	public Page findByPageWithInListCondition(Page page) {
		return this.getDao().findByPageWithInListCondition(page);
	}
	public Page findByPageWithExpression(Page page,List<Criterion> args) {
		return this.getDao().findByPageWithExpression(page,args);
	}

}
