package com.zb.jnlxc.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.ZLHW.base.Form.Page;
import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.AuthorDetail;
import com.zb.jnlxc.model.Depart;
import com.zb.jnlxc.model.UserGroup;
import com.zb.jnlxc.model.UserGroup_Author;


@SuppressWarnings("unchecked")
@Transactional
@Service
public class AdminService extends BaseService<AdminDAO,Admin, Integer> {
	private static final Log log = LogFactory.getLog(AdminService.class);
	@Resource
	private UserGroupDAO userGroupDAO;	
	@Resource
	private AuthorDetailDAO authorDetailDAO;
	@Resource
	private UserGroup_AuthorDAO userGroup_AuthorDAO;
	@Resource
	private SchemeDAO schemeDAO;
    @Resource
    private DepartDAO departDAO;
    @Resource
    private AdminUserGroupService adminUserGroupService;


    public Admin adminExist(Admin user){
        List<Admin> l = getDao().findByHQL("from Admin a where a.account=?",user.getAccount());
        return l.size()==0?null:l.get(0);
    }



	public void editUserGroup(String name,int usergroupId) throws BaseErrorModel{
		UserGroup userGroup=userGroupDAO.getById(usergroupId);
		userGroup.setName(name);
		userGroupDAO.update(userGroup);
	}
	
	public boolean editPassword(Admin admin,String oldPass,String newPass) throws BaseErrorModel{
		Admin loginUser = getDao().getById(admin.getDbId());
		if(!oldPass.equals(loginUser.getPassword())){
			return false;
		}else{
			loginUser.setPassword(newPass);
			this.getDao().update(loginUser);
		}
		return true;
	}
	
	public boolean editPasswordById(Integer dbid,String newPass) throws BaseErrorModel{
		Admin loginUser = getDao().getById(dbid);
		loginUser.setPassword(newPass);
		this.getDao().update(loginUser);
		return true;
	}
	
	public boolean resetPassword(Integer dbid) throws BaseErrorModel{
		Admin loginUser = getDao().getById(dbid);
		loginUser.setPassword("123456");
		this.getDao().update(loginUser);
		return true;
	}
	
	public boolean havePower(Admin user,String authorDetailName) {
		List list=getDao().findByHQL("from AdminUserGroup au,UserGroup_Author uga " +
				"where au.userGroup=uga.userGroup and au.admin=? and uga.authorDetail.name=?", user,authorDetailName);
		return list.size()!=0;
	}

    /**
     * 查询所有权限
     * @return
     */
    public List<AuthorDetail> getAuthorDetails() {
        return authorDetailDAO.findAll();
    }

	public List<AuthorDetail> getViewTablesByAdmin(Admin user) {
	    return authorDetailDAO.getViewTablesByAdmin(user);
	}

    public List<AuthorDetail> getAuthorDetailsByGroup(UserGroup userGroup){
        return authorDetailDAO.getAuthorDetailsByGroup(userGroup);
    }


	public List<UserGroup> findAllUserGroup(){
        return userGroupDAO.findAll();
	}

    public Page findAdmins(Page page) {
        StringBuilder hql = new StringBuilder("from Admin ");
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        return this.getDao().findByPageWithHQL(page, hql.toString());
    }


	
	//查找拥有某个权限的所有用户
	public List<Admin> getPowerAdmins(String powerName){
		return this.getDao().findAdminsByAuthorName(powerName);
	}
	//查找拥有某个权限的所有用户
	public List<Admin> getPowerAdmins(Integer id){
        return this.getDao().findAdminsByAuthorId(id);
	}

    //修改某个用户组的权限
    public void editAuthorDetails(UserGroup userGrup, Set<Integer> ds) {
        userGroupDAO.refresh(userGrup);
        userGroup_AuthorDAO.deleteByGroup(userGrup);
        editAuthorDetailsRecurrence(userGrup,ds);
    }
    //递归去更新用户组权限
    private void editAuthorDetailsRecurrence(UserGroup userGrup, Set<Integer> ds){
        Set<Integer> parentSet = new HashSet<Integer>();
        for (int dbId:ds){
            AuthorDetail detail = authorDetailDAO.loadById(dbId);
            if(detail.getParentId()!=null) {
                parentSet.add(detail.getParentId());
            }
            UserGroup_Author ua = new UserGroup_Author();
            ua.setUserGroup(userGrup);
            ua.setAuthorDetail(detail);
            userGroup_AuthorDAO.create(ua);
        }
        if(parentSet.size()>0){
            editAuthorDetailsRecurrence(userGrup,parentSet);
        }
    }


	public Admin create(Admin admin) throws BaseErrorModel{
        if(StringUtils.isEmpty(admin.getPassword()))
            admin.setPassword("123456");
        List<Admin> l = this.getByColumn("account",admin.getAccount());
        if(l!=null&&l.size()>0)
            throw new BaseErrorModel("已经存在的帐号","");
		admin.setNextClientNum(1);
		super.create(admin);
        adminUserGroupService.createForAdmin(admin);
		return admin;
	}

    public void update(Admin entity) throws BaseErrorModel {
        if(entity.getDepart()!=null)
            departDAO.refresh(entity.getDepart());
        entity.setAddDate(new Date());
        entity.setLastlogin(new Date());
        adminUserGroupService.createForAdmin(entity);
        super.update(entity);
    }

	/**
	 * 查找所有业务员
	 * @return
	 */
	public List findAllAgent(){
		return this.getDao().findAllAgent();
	}
	/**
	 * 查找所有修模工
	 * @return
	 */
	public List findAllRepairer(){
		return this.getDao().findAllRepairer();
	}
	/**
	 * 查找所有的外协负责人
	 * @return
	 */
	public List findAllWaixie(){
		return this.getDao().findAllWaixie();
	}
	
	/**
	 * 查找所有的机台用户
	 * @return
	 */
	public List findAllJiTai(){
		return this.getDao().findAllJiTai();
	}

    public List<Admin> findByName(String key) {
        key = key==null?"":key.trim();
        key = "%"+key+"%";
        return this.getDao().findByHQL("from Admin where name like ? or account like ?",key,key);
    }

    /**
     * 拥有开单权限的人员
     * @param user
     * @return
     */
    public boolean isSuperAgent(Admin user){
        return havePower(user, "开单");
    }

    /**
     * 是否是超级管理员
     * @param dbId
     * @return
     */
    public boolean isSuperAdmin(Integer dbId){
        Admin admin = this.getDao().getById(dbId);
        return admin.getUserGroups().contains("100");
    }

    /**
     * 修改用户密码
     * @param dbId
     * @param newPassword
     */
    public void changePassword(Integer dbId, String newPassword) {
        Admin admin = this.loadById(dbId);
        admin.setPassword(newPassword);
        this.update(admin);
    }
}
