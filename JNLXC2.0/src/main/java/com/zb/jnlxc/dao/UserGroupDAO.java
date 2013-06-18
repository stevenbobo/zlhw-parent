package com.zb.jnlxc.dao;
import com.zb.jnlxc.model.Admin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.UserGroup;

import java.util.List;


@Component
public class UserGroupDAO extends DAO<UserGroup,Integer> {
	private static final Log log = LogFactory.getLog(UserGroupDAO.class);
	public UserGroup findByName(String name){
		return this.findByHQL("from UserGroup u where u.name=?", name).get(0);
	}
    public List<UserGroup> getUserGroupsByAdmin(Admin admin){
        return this.findByHQL("select t.userGroup from AdminUserGroup t where t.admin=?", admin);
    }

    public List<UserGroup> findAllUserGroup(Admin user){
        List<UserGroup> userGroups = getUserGroupsByAdmin(user);
        byte lever =10;
        for (UserGroup userGroup:userGroups){
            if(userGroup.getGroupLever()<lever)
                lever=userGroup.getGroupLever();
        }
        return this.findByHQL("from UserGroup u where u.groupLever>=?", lever);
    }
}
