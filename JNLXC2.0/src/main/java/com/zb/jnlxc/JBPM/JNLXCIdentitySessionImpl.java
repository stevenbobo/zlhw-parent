package com.zb.jnlxc.JBPM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.UserGroup;
import org.jbpm.api.JbpmException;
import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.jbpm.pvm.internal.util.CollectionUtil;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.dao.AdminDAO;
import com.zb.jnlxc.dao.UserGroupDAO;

public class JNLXCIdentitySessionImpl implements IdentitySession {
	private AdminDAO adminDAO = (AdminDAO) BeanFactory.LookUp("adminDAO");
	private UserGroupDAO userGroupDAO = (UserGroupDAO) BeanFactory
			.LookUp("userGroupDAO");
	public JNLXCIdentitySessionImpl(){
	}

//	protected Session session;
//	public void setSession(Session session) {
//	    this.session = session;
//	  }

	  public User findUserById(String userId){
		  User user=null;
	     try {
			user=(User)adminDAO.getUserByAccount(userId);
		} catch (BaseErrorModel e) {
			e.printStackTrace();
		}
		return user;
	  }

	  public List<User> findUsersById(String[] userIds)
	  {
		  List users=adminDAO.findByHQL("from Admin a where a.account in (?)", userIds);
	    if (userIds.length != users.size())
	      throw new JbpmException("not all users were found: " + Arrays.toString(userIds));
	    return CollectionUtil.checkList(users, User.class);
	  }

	  public List<User> findUsers() {
	    List users =adminDAO.findAll();
	    return CollectionUtil.checkList(users, User.class);
	  }

	  public List<User> findUsersByGroup(String groupId) {
		  List users=adminDAO.getUsersByGroupName(groupId);
	    return CollectionUtil.checkList(users, User.class);
	  }

	  public Group findGroupById(String groupId) {
	    return (Group)userGroupDAO.findByName(groupId);
	  }

	  public List<Group> findGroupsByUserAndGroupType(String userId, String groupType)
	  {
		  List<Group> groups=new ArrayList();
		    try {
                Admin admin = adminDAO.getUserByAccount(userId);
                List<UserGroup> l =userGroupDAO.getUserGroupsByAdmin(admin);
				groups.addAll(l);
			} catch (BaseErrorModel e) {
				e.printStackTrace();
			}
		    return CollectionUtil.checkList(groups, Group.class);
	  }

	  public List<Group> findGroupsByUser(String userId) {
		  List groups=new ArrayList();
	    try {
            Admin admin = adminDAO.getUserByAccount(userId);
            List<UserGroup> l =userGroupDAO.getUserGroupsByAdmin(admin);
			groups.addAll(l);
		} catch (BaseErrorModel e) {
			e.printStackTrace();
		}
	    return CollectionUtil.checkList(groups, Group.class);
	  }

	  public List<Group> findGroups() {
	    List groups = userGroupDAO.findAll();
	    return CollectionUtil.checkList(groups, Group.class);
	  }

	  public void deleteGroup(String groupId){}

	  public void createMembership(String userId, String groupId, String role) {}

	  public void deleteMembership(String userId, String groupId, String role) {}

	  public void deleteUser(String userId){}

	  public String createGroup(String groupName, String groupType, String parentGroupId) { return null;}
	  public String createUser(String userName, String givenName, String familyName, String businessEmail){return null;}
}
