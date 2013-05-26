package com.ZLHW.base.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.SimpleExpression;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
public interface IDAO<BEAN,PRIMARY> {
	/**
	 * 获得sessionFactory
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);
	/**
	 * 获取session
	 * @return
	 */
	public Session getSession() ;
    /**
     * 新增记录
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
	 * 更新对象
	 * @param entity
	 * @throws BaseErrorModel
	 */
	public void refresh(BEAN entity) throws BaseErrorModel;
	/**
	 * 更新或保存
	 * @param entity
	 * @throws BaseErrorModel
	 */
	public void saveOrupdate(BEAN entity) throws BaseErrorModel;
	/**
	 * 但session有两个主键相同的对象是，用此方法修改
	 * @param entity
	 */
	public void merger(BEAN entity) throws BaseErrorModel;
    /**
     * 用HQL语句做更新或删除
     * @param hql
     * @param args
     * @return
     */
	public int updateHql(String hql, Object... args) throws BaseErrorModel;
    /**
     * 用存在映射文件中的HQL或sql语句做更新或删除
     * @param nameQuery
     * @param args
     * @return
     */
	public int updateNamedQuery(String nameQuery, Object... args) throws BaseErrorModel;
	/**
	 *  用存在映射文件中的HQL或sql语句做查询
	 *  查询结果根据查询条件而定
	 * @param nameQuery
	 * @param args
	 * @return
	 */
	public List findNamedQuery(String nameQuery, Object... args) ;
	/**
	 *  分页查询
	 * @param page
	 * @return List
	 */
	public Page findAllByPage(Page page) ;
	/**
	 *  通过完全匹配Page中的限定条件进行分页查询
	 * @param page
	 * @return List
	 */
	public Page findByPageWithEqCondition(Page page) ;
	/**
	 *  通过模糊匹配Page中的限定条件进行分页查询
	 * @param page
	 * @return List
	 */
	public Page findByPageWithLikeCondition(Page page) ;
	/**
	 *  通过匹配Page限定条件中的开始时间和结束时间进行分页查询
	 * @param page
	 * @return List
	 */
	public Page findByPageWithTimeCondition(Page page) ;
	/**
	 *  通过匹配Page限定条件中的集合类进行分页查询
	 * @param page
	 * @return List
	 */
	public Page findByPageWithInListCondition(Page page) ;
	/**
	 *  通过Criterion数组查询 不通过queryCondition
	 * @param page
	 * @return List
	 */
	public Page findByPageWithExpression(Page page,List<Criterion> args) ;
	/**
	 *  通过HQL查询 
	 * @param page
	 * @return List
	 */
	public Page findByPageWithHQL(Page page,String HQL, Object...args);
	/**
	 *  通过SQL查询 
	 * @param page
	 * @return List
	 */
	public Page findByPageWithSQL(Page page,String SQL, String...args);
	/**
	 * 通过HQL语句做查询
	 * @param HQL
	 * @param args
	 * @return
	 */
	public List findByHQL(String HQL, Object... args) ;
	/**
	 * 以:name的形式
	 * @param page
	 * @param HQL
	 * @param queryConditions
	 * @return
	 */
	public Page findByPageWithTmpHQL(Page page,String HQL, List<QueryCondition> queryConditions);

    /**
     * 通过SQL语句做查询
     * @param SQL
     * @param args
     * @return
     */
	public List<Object> findBySQL(String SQL, String... args) ;
    /**
     * 通过HQL语句做查询、
     * @param hql
     * @param fromIdx 起始记录
     * @param fetchCount 总记录条数
     * @param args
     * @return
     */
	public List findByHQL(String hql, int fromIdx, int fetchCount,
			Object... args) ;
	/**
	 * 通过tb.startdate <:endDate的形式做查询     
	 * @param hql
	 * @param queryConditions
	 * @return
	 */
	public List<BEAN> findByTmpHQL(String hql,List<QueryCondition> queryConditions);

	/**
	 * 通过:endDate的形式做分页查询
	 * @param hql
	 * @param fromIdx
	 * @param fetchCount
	 * @param queryConditions
	 * @return
	 */
	public List<BEAN> findByTmpHQL(String hql, int fromIdx, int fetchCount,
			List<QueryCondition> queryConditions);
    /**
     * 查找所有记录
     * @return
     */
	public List<BEAN> findAll() ;
	/**
     * 查找所有记录 根据id正序排列
     * @return
     */
	public List<BEAN> findAllAsc();
    /**
     * 查询指定记录
     * @param pageNo 起始记录
     * @param pageSize 总记录条数
     * @return
     */
	public List<BEAN> findList(int pageNo, int pageSize) ;
	/**
	 * 当前记录总数
	 * @return
	 */
	public int getCountOfAll() ;
    /**
     * 根据HQL语句获得总记录数
     * @param args
     * @return
     */
	public int getCountOfAll(String HQL, Object... args) ;
	/**
	 * 以:name形式查总记录数
	 * @param HQL
	 * @param queryConditions
	 * @return
	 */
	public int getCountOfAllByTmp(String HQL, List<QueryCondition> queryConditions);
	 /**
     * 根据SQL语句获得总记录数
     * @param SQL
     * @param args
     * @return
     */
	public int getCountOfAllBySQL(String SQL, String... args) ;
	/**
	 * 根据主键加载实体
	 * @param id
	 * @return
	 */
	public BEAN loadById(PRIMARY id) ;
    /**
     * 根据主键获得实体
     * @param id
     * @return
     */
	public BEAN getById(PRIMARY id) ;
	/**
     * 根据某个字段获取查询数组
     * @return
     */
	public List<BEAN> getByColumn(String columnName, Object arg) ;
	/**
     * 根据某个字段获取查询数组,正序排列
     * @return
     */
	public List<BEAN> getByColumnAsc(String columnName, Object arg) ;
	/**
     * 更具主键删除实体
     * @param id
     */
	public void deleteById(PRIMARY id) throws BaseErrorModel;
}
