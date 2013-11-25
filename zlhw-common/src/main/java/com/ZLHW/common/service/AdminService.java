package com.ZLHW.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;
import com.ZLHW.common.dao.AdminDAO;
import com.ZLHW.common.dao.AuthorDAO;
import com.ZLHW.common.dao.AuthorDetailDAO;
import com.ZLHW.common.dao.JobDAO;
import com.ZLHW.common.dao.OrganDAO;
import com.ZLHW.common.dao.Organ_JobDAO;
import com.ZLHW.common.dao.RoleDAO;
import com.ZLHW.common.dao.Role_AuthorDetailDAO;
import com.ZLHW.common.enums.EnumAdminStatus;
import com.ZLHW.common.model.Admin;
import com.ZLHW.common.model.Author;
import com.ZLHW.common.model.AuthorDetail;
import com.ZLHW.common.model.Organ;
import com.ZLHW.common.model.Organ_Job;
import com.ZLHW.common.model.Role;
import com.ZLHW.common.model.Role_AuthorDetail;
import com.ZLHW.common.model.TreeNode;


@Transactional
@Service
public class AdminService extends BaseService<AdminDAO,Admin, Integer> {
	Logger logger = LoggerFactory.getLogger(AdminService.class);
	@Autowired
	private AuthorDAO authorDAO;
	@Autowired
	private RoleDAO roleDAO;	
	@Autowired
	private AuthorDetailDAO authorDetailDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private Role_AuthorDetailDAO role_AuthorDetailDAO;
	@Autowired
	private AdminDAO adminDao;
	@Autowired
	private Organ_JobDAO organ_JobDAO;
	
	public static Map<String, String> userMap = new ConcurrentHashMap<String, String>();
	
	/**
	 * 添加角色，并默认赋予退出系统和登录的权限
	 * @param name
	 * @throws BaseErrorModel
	 */
	public void addUserGroup(String name) throws BaseErrorModel{
		Role role=new Role();
		role.setName(name);
		roleDAO.create(role);
		
		/** 获取默认权限 */
		List<AuthorDetail> list = authorDetailDAO.getDefaultAuthor();
		for(int i=0;i<list.size();i++){
			/** 添加默认权限 */
			Role_AuthorDetail role_AuthorDetail=new Role_AuthorDetail();
			role_AuthorDetail.setAuthorDetail(list.get(i));
			role_AuthorDetail.setRole(role);
			role_AuthorDetailDAO.create(role_AuthorDetail);
		}
	}
	
	public void editRole(String name,int roleId) throws BaseErrorModel{
		Role role=roleDAO.getById(roleId);
		role.setName(name);
		roleDAO.update(role);
	}
	
	public boolean editPassword(Admin admin,String oldPass,String newPass) throws BaseErrorModel{
		Admin loginUser = getById(admin.getDbId());
		if(!oldPass.equals(loginUser.getPassword())){
			return false;
		}else{
			loginUser.setPassword(newPass);
			update(loginUser);
		}
		return true;
	}
	
	public boolean editPasswordById(Integer dbid,String newPass) throws BaseErrorModel{
		Admin loginUser = getById(dbid);
		loginUser.setPassword(newPass);
		update(loginUser);
		return true;
	}
	
	public boolean resetPassword(Integer dbid) throws BaseErrorModel{
		Admin loginUser = getById(dbid);
		loginUser.setPassword("123456");
		update(loginUser);
		return true;
	}
//	/**
//	 * 判断用户是否拥有权限
//	 * @param user
//	 * @param beanName
//	 * @param methodName
//	 * @return
//	 */
//	public boolean havePower(Admin user, String beanName, String methodName) {
//		boolean temp=false;
//		List list=adminDao.findByHQLWithIndex("from UserGroup_Author uga " +
//				"where uga.userGroup = ? and uga.authorDetail.viewTable.beanName=? " +
//				"and uga.authorDetail.viewTable.beanFunction=?", user.getUserGroup(),beanName,methodName);
//		if(list.size()!=0){
//			temp=true;
//		}
//		return temp;
//	}


	/**
	 * 查询用户权限明细
	 */
	public List<AuthorDetail> getAuthorDetailsByAdmin(Admin user) {
		String hql="select distinct t1.authorDetail from " +
		"Role_AuthorDetail t1,Job_Role t2,Organ_Job t3 " +
		"where t1.role=t2.role and t2.job=t3.job and t3 =?";
		List<AuthorDetail> authorDetailList=authorDetailDAO.findByHQL(hql, user.getOrgan_Job());
		return authorDetailList;
	}
	/**
	 * 查询菜单明细
	 * @param user
	 * @return
	 */
	public List<AuthorDetail> getMenuDetailsByAdmin(Admin user) {
		String hql="select distinct t1.authorDetail from " +
		"Role_AuthorDetail t1,Job_Role t2,Organ_Job t3 " +
		"where t1.role=t2.role and t2.job=t3.job and t3 =? and t1.authorDetail.status=0";//菜单显示的状态码为0
		List<AuthorDetail> authorDetailList=authorDetailDAO.findByHQL(hql, user.getOrgan_Job());
		Collections.sort(authorDetailList, new Comparator(){
			public int compare(Object o1, Object o2) {
				AuthorDetail ad1=(AuthorDetail) o1;
				AuthorDetail ad2=(AuthorDetail) o2;
				if(ad1.getDbId()<ad2.getDbId())
					return 1;
				else
					return 0;
			}
			
		});
		return authorDetailList;
	}


	/**
	 * 	获取一级菜单
	 * @param user
	 * @return
	 */
	public List<Author> getAuthorsByAdmin(Admin user) {
		String hql="select distinct t1.authorDetail.author from " +
			"Role_AuthorDetail t1,Job_Role t2,Organ_Job t3 " +
			"where t1.role=t2.role and t2.job=t3.job and t3 =? and t1.authorDetail.status=0";//菜单显示的状态码为0
		List<Author> authorList=authorDAO.findByHQL(hql, user.getOrgan_Job());
		return authorList;
	}


//	//用户登录验证
//	public Map login(Admin user) throws BaseErrorModel{
//		Map map=new HashMap();
//		List<Admin> l= this.findHql("from Admin a where a.account=?",user.getAccount());
//		if(l.size()==0){
//			throw new BaseErrorModel("无此用户","baseview");
//		}else{
//			Admin admin=l.get(0);
//			if(user.getPassword().equals(admin.getPassword())){
//				this.getFlexSession().setAttribute("Admin", admin);
//				userMap.put(admin.getAccount(), this.getFlexSession().getId());
//				map.put("admin", admin);
//			}else{
//				throw new BaseErrorModel("密码错误","baseview");
//			}
//		}
//		return map;
//	}
//
//	/**
//	 * 获取当前用户
//	 */
//	public Admin getAdmin(){
//		return (Admin) getFlexSession().getAttribute("Admin");
//	}
//
//	/**
//	 * 用户退出
//	 */
//	public void loginOut(){
//		logger.info(this.getAdmin().getAccount()+"退出");
//		this.getFlexSession().unregisterFlexClient(FlexContext.getFlexClient());
//	}

	//查找拥有某个权限的所有用户
	public List<Admin> getPowerAdmins(String powerName){
		String hql="select distinct t4 from " +
		"Role_AuthorDetail t1,Job_Role t2,Organ_Job t3,Admin t4 " +
		"where t1.role=t2.role and t2.job=t3.job and t3 =t4.organ_Job and t1.authorDetail.name=?";
		return findHql(hql,powerName);
	}

	/**
	 * 获取当前用户菜单
	 * @return
	 */
	public List<Author> getMenu(Admin user){
		//获取当前用户权限
		List<Author> authors = new ArrayList();
		//获取当前用户权限明细
		List<AuthorDetail>   authorDetails = this.getMenuDetailsByAdmin(user);
		//将权限明细组装到对应的用户权限中,便于前端显示
			for(AuthorDetail authorDetail : authorDetails){
				List<AuthorDetail>  children=authorDetail.getAuthor().getChildren();
				if(children==null){
					authors.add(authorDetail.getAuthor());
					children = new ArrayList();
					authorDetail.getAuthor().setChildren(children);
				}
					children.add(authorDetail);
			}
		    Collections.sort(authors, new Comparator<Author>(){
				public int compare(Author o1, Author o2) {
					if(o1.getDbId()<o2.getDbId()){
						return 0;
					}else{
						return 1;
					}
				}});
		return authors;
	}
	
	//查找拥有某个权限的所有用户
	public List<Admin> getPowerAdmins(Integer id){
		String hql="select distinct t4 from " +
		"Role_AuthorDetail t1,Job_Role t2,Organ_Job t3,Admin t4 " +
		"where t1.role=t2.role and t2.job=t3.job and t3 =t4.organ_Job and t1.authorDetail.dbId=?";
		return findHql(hql,id);
	}
	
	public List<AuthorDetail> getAuthorDetailsByAuthor(Author author) {		
		List<AuthorDetail> authorDetails=this.findHql("from AuthorDetail ad " +
				"where ad.author=? order by ad.id asc", author);
		return authorDetails;
	}

	/**
	 * 获取用户所有的权限
	 * @return
	 */
	public List<AuthorDetail> getAuthorDetails(Admin user){
		return getAuthorDetailsByAdmin(user);
	}
	//获取当前用户初始化环境
	public Map getInitInfo(Admin user) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		try
		{
			//获取当前用户权限
			List<Author> authors = this.getMenu(user);
			map.put("authors", authors);
		}catch (Exception e) {
			// TODO: handle exception
			BaseErrorModel error=new BaseErrorModel("初始化用户失败","baseview");
			map.put("error", error);
			e.printStackTrace();
		}
		return map;
	}
	
	//获取修改权限所需数据
	@SuppressWarnings("unchecked")
	public Map getAuthorInfo()
	{
		Map map = new HashMap();
		List<Admin> users = findAll();
		List<Role>  userGroups = roleDAO.findAll();
		List<Author> authors = authorDAO.findAll();
		//在权限中放入所有的权限明细
		for(Author author : authors){
			author.setChildren(this.getAuthorDetailsByAuthor(author));
		}
		List<Role_AuthorDetail> userGroup_Author = role_AuthorDetailDAO.findAll();
		if(users == null)
			map.put("error", new BaseErrorModel("当前没有用户","baseview"));
		else if(userGroups == null)
			map.put("error", new BaseErrorModel("当前没有用户组可供选择","baseview"));
		else if(authors == null)
			map.put("error", new BaseErrorModel("当前没有权限可供分配","baseview"));
		else if(userGroup_Author == null)
			map.put("error", new BaseErrorModel("权限中间表错误","baseview"));
		else
		{
			map.put("users", users);
			map.put("userGroups", userGroups);
			map.put("authors", authors);
			map.put("userGroup_Author", userGroup_Author);
		}
		return map;
	}
	
	//修改某个用户的岗位机构
	@SuppressWarnings("unchecked")
	public Map editAuthor(Admin user,Organ_Job organ_Job) throws BaseErrorModel{
		Map map = new HashMap();
		user.setOrgan_Job(organ_Job);
		update(user);
		return map;
	}

	//修改某个角色的权限
	@SuppressWarnings("unchecked")
	public Map editUserGroup(Role role,List<Author> authors) throws BaseErrorModel{
		Map map = new HashMap();
		List<Role_AuthorDetail> role_AuthorDetails = role_AuthorDetailDAO.findByHQL("from Role_AuthorDetail ua where ua.role = ?", role);
		for(Role_AuthorDetail userGroup_Author : role_AuthorDetails)
		{
			role_AuthorDetailDAO.delete(userGroup_Author);
		}
		for(Author author : authors)
		{
			for(AuthorDetail authorDetail : author.getChildren())
			{
				if(authorDetail.getSelected()){
					Role_AuthorDetail role_AuthorDetail = new Role_AuthorDetail();
					role_AuthorDetail.setAuthorDetail(authorDetail);
					role_AuthorDetail.setRole(role);
					role_AuthorDetailDAO.create(role_AuthorDetail);
				}
			}
		}
		return map;
	}
	
	public Admin create(Admin admin) throws BaseErrorModel{
		admin.setNextClientNum(1);
		super.create(admin);
		return admin;
	}
	
	/**
	 * 查找所有业务员
	 * @return
	 */
	public List findAllAgent(){
		return findAllAgent();
	}
	
	

	/*-------------前台用户管理调用的方法开始---------------*/
	public List<TreeNode> getJobTree(){
		List<TreeNode> treeList = new LinkedList<TreeNode>();
		List<Organ> organList = organDAO.findAll();
		for(Organ organ : organList){
			if(organ.getLever() == OrganService.ROOT_LEVEL){
				TreeNode treeNode = new TreeNode();
				treeNode.setData(organ);
				treeNode.setText(organ.getName());
				treeList.add(treeNode);
				generateChildTree(treeNode,organList);
			}
		}
		return treeList;
	}

	private void generateChildTree(TreeNode treeNode,List<Organ> organList){
		if(treeNode.getChildren()==null)
			treeNode.setChildren(new LinkedList<TreeNode>());
		Organ organ = (Organ) treeNode.getData();
		List<Organ_Job> organ_JobList = organ_JobDAO.getByColumn("organ", organ);
		for(Organ_Job organ_Job : organ_JobList){
			TreeNode childNode = new TreeNode();
			childNode.setData(organ_Job);
			childNode.setText(organ_Job.getJob().getName());
			treeNode.getChildren().add(childNode);
		}
		for(Organ childOrgan : organList){
			if(organ.getDbId().intValue()==childOrgan.getParent().intValue()){
				TreeNode childNode = new TreeNode();
				childNode.setData(childOrgan);
				childNode.setText(childOrgan.getName());
				treeNode.getChildren().add(childNode);
				generateChildTree(childNode,organList);
			}
		}
	}
	public List getAdminStatusList(){
		return EnumAdminStatus.toList();
	}
	public Map getAdminStatusMap(){
		return EnumAdminStatus.toMap();
	}
}
