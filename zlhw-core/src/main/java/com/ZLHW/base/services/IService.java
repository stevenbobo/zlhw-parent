package com.ZLHW.base.services;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;

import java.util.List;

public interface IService<BEAN,PRIMARY> {
	/**
	 * 增加记录
	 * @param entity
	 */
	public BEAN create(BEAN entity) throws BaseErrorModel;
	/**
	 * 删除记录
	 * @param entity
	 */
	public void delete(BEAN entity) throws BaseErrorModel;
    /**
     * 更新记录
     * @param entity
     */
	public void update(BEAN entity) throws BaseErrorModel;
	/**
	 * 刷新对象
	 * @param entity
	 * @return
	 */
	public BEAN refresh(BEAN entity) throws BaseErrorModel;
    /**
     * 通过HQL语句做查询、
     * @param hql
     * @param fromIdx 起始记录
     * @param fetchCount 总记录条数
     * @param args
     * @return
     */
	public List findHql(String hql, int fromIdx, int fetchCount,
                        Object... args) ;
//	public List findHqlCached(String hql,int time, int fromIdx, int fetchCount,
//			Object... args) ;
    /**
     * 查找所有记录
     * @return
     */
	public List<BEAN> findAll() ;
//	public List<BEAN> findAllCached(int time) ;
    /**
     * 查询指定记录
     * @param pageNo 起始记录
     * @param pageSize 总记录条数
     * @return
     */
	public List<BEAN> findList(int pageNo, int pageSize) ;
//	public List<BEAN> findListCached(int time,int pageNo, int pageSize) ;
	/**
	 * 分页查询
	 */
	public Page findAllByPage(Page page) ;
	/**
	 * 当前记录总数
	 * @return
	 */
	public int getCountOfAll() ;
//	public int getCountOfAllCached(int time) ;
	/**
	 * 根据hql语句获得总记录数
	 * @param HQL hql查询语句
	 * @param args 查询条件
	 * @return
	 */
	public int getCountOfAll(String HQL, Object... args) ;
//	public int getCountOfAllCached(String HQL ,int time, String... args) ;
    /**
     * 根据主键获得实体
     * @param id
     * @return
     */
	public BEAN getById(PRIMARY id) ;
//	public BEAN getByIdCached(int time,PRIMARY id) ;
	/**
	 * 根据主键载入实体
	 * @param id
	 * @return
	 */
	public BEAN loadById(PRIMARY id) ;
    /**
     * 更具主键删除实体
     * @param id
     */
	public void deleteById(PRIMARY id) throws BaseErrorModel;
	public void deleteById(PRIMARY[] ids) throws BaseErrorModel;
}
