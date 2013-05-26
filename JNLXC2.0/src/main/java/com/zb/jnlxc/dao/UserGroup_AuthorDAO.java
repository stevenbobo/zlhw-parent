package com.zb.jnlxc.dao;
import java.util.ArrayList;
import java.util.List;

import com.zb.jnlxc.model.UserGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.AuthorDetail;
import com.zb.jnlxc.model.UserGroup_Author;

@Component
public class UserGroup_AuthorDAO extends DAO<UserGroup_Author,Integer> {
	private static final Log log = LogFactory.getLog(UserGroup_AuthorDAO.class);

	public UserGroup_AuthorDAO() {
		super();
	}
	




	public List<AuthorDetail> getViewTablesByAuthorDetail(AuthorDetail authorDetail) {
		List<UserGroup_Author> list=findByHQL("from UserGroup_Author uga " +
				"where uga.authorDetail=? order by uga.id asc", authorDetail);
		List<AuthorDetail> viewList=new ArrayList<AuthorDetail>();
		for(UserGroup_Author uga:list){
			viewList.add(uga.getAuthorDetail());
		}
		return viewList;
	}

    /**
     * 按照组id删除所有明细对应关系
     * @param userGrup
     */
    public void deleteByGroup(UserGroup userGrup) {
        updateHql("delete UserGroup_Author s where s.userGroup.dbId=?",userGrup.getDbId());
    }

}
