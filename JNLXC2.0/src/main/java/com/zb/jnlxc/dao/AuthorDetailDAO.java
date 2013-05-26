package com.zb.jnlxc.dao;
import java.util.ArrayList;
import java.util.List;

import com.zb.jnlxc.model.UserGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.TransientObjectException;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.AuthorDetail;

import javax.annotation.Resource;

@Component
public class AuthorDetailDAO extends DAO<AuthorDetail,Integer> {
	private static final Log log = LogFactory.getLog(AuthorDetailDAO.class);
    @Resource
    UserGroupDAO userGroupDAO;

	public AuthorDetailDAO() {
		super();
	}

	/** 获取默认权限 */
	public List<AuthorDetail> getDefaultAuthor(){
		return findByHQL("from AuthorDetail t where t.dbId like '8%' ");
	}
	
	public List<AuthorDetail> getAuthorDetailsByAuthor(String parentId) {
		List<AuthorDetail> authorDetails=findByHQL("from AuthorDetail ad " +
				"where ad.parentId=? order by ad.dbId asc", parentId);
		return authorDetails;
	}
	
	public List<AuthorDetail> getViewTablesByAdmin(Admin user) {
		String hql= "select au.authorDetail from UserGroup_Author au "+
                "where au.userGroup in(:groups) " +
                "and au.authorDetail.status=0 " +
                "group by au.authorDetail " +
                "order by au.authorDetail asc";
        Query query = this.getSession().createQuery(hql);
        List userGroups = userGroupDAO.getUserGroupsByAdmin(user);
        query.setParameterList("groups",userGroups);
        return query.list();
    }

    public List<AuthorDetail> getAuthorDetailsByGroup (UserGroup userGroup){
        try{
        String hql="select distinct(au.authorDetail) from UserGroup_Author au where au.userGroup=?";
        return findByHQL(hql, userGroup);
        }catch (TransientObjectException e){
            return  new ArrayList<AuthorDetail>();
        }
    }

}
