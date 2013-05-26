package com.ZLHW.common.service;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.AuthorDAO;
import com.ZLHW.common.dao.AuthorDetailDAO;
import com.ZLHW.common.dao.RoleDAO;
import com.ZLHW.common.dao.Role_AuthorDetailDAO;
import com.ZLHW.common.model.Author;
import com.ZLHW.common.model.AuthorDetail;
import com.ZLHW.common.model.Role;
import com.ZLHW.common.model.Role_AuthorDetail;
import com.ZLHW.common.model.TreeNode;
@Transactional
@Service("RoleService")
public class RoleService extends BaseService<RoleDAO,Role, Integer> {
	private static final Log log = LogFactory.getLog(RoleService.class);
	@Autowired
	private RoleDAO roleDao;
	@Autowired
	private AuthorDAO authorDao;
	@Autowired
	private AuthorDetailDAO authorDetailDao;
	@Autowired
	private Role_AuthorDetailDAO role_AuthorDetailDao;
	
	/**
	 * 获取关联角色的权限树
	 * @param role
	 * @return
	 */
	public List<TreeNode> getAuthorTreeByRole(Role role){
		List<Role_AuthorDetail> role_AuthorDetailList = role_AuthorDetailDao.getByColumn("role", role);
		List<Author> authorList = authorDao.findAll();
		List<AuthorDetail> authorDetailList = authorDetailDao.findAll();
		List<TreeNode> treeNodeList = new LinkedList<TreeNode>();
		for(Author author : authorList){
			TreeNode node = new TreeNode();
			node.setData(author);
			node.setText(author.getName());
			node.setChildren(new LinkedList<TreeNode>());
			treeNodeList.add(node);
			//权限下的权限明细是否被全选的标志
			boolean selectFlag = true;
			int countFlag = 0;
			
			for(AuthorDetail authorDetail:authorDetailList){
				if(author.equalWithByPrimary(authorDetail.getAuthor())){
					TreeNode childNode = new TreeNode();
					childNode.setData(authorDetail);
					childNode.setText(authorDetail.getName());
					node.getChildren().add(childNode);
					if(role_AuthorDetailList==null || role_AuthorDetailList.size()<1)
						selectFlag = false;
					for(Role_AuthorDetail role_AuthorDetail : role_AuthorDetailList){
						if(authorDetail.equalWithByPrimary(role_AuthorDetail.getAuthorDetail())){
							childNode.setSelected(true);
							role_AuthorDetailList.remove(role_AuthorDetail);
							break;
						}else{
							childNode.setSelected(false);
							selectFlag = false;
						}
					}
					countFlag++;
				}
			}
			if(countFlag==0)
				selectFlag = false;
			node.setSelected(selectFlag);
		}
		return treeNodeList;
	}
	/**
	 * 根据关联角色的权限树更新中间表Role_AuthorDetail
	 * @param treeList
	 * @param role
	 * @param delAll
	 */
	public void updateAuthorTreeByRole(List<TreeNode> treeList ,Role role,boolean delAll){
		if(treeList==null || role==null)
			return;
		
		if(delAll){
			List<Role_AuthorDetail> role_AuthorDetailList = role_AuthorDetailDao.getByColumn("role", role);
			role_AuthorDetailDao.delete(role_AuthorDetailList);
		}
		for(TreeNode treeNode : treeList){
			if(treeNode.getData().getClass().equals(AuthorDetail.class)){
				if(treeNode.isSelected()){
					Role_AuthorDetail role_AuthorDetail = new Role_AuthorDetail();
					role_AuthorDetail.setAuthorDetail((AuthorDetail) treeNode.getData());
					role_AuthorDetail.setRole(role);
					role_AuthorDetailDao.create(role_AuthorDetail);
				}
			}
			updateAuthorTreeByRole(treeNode.getChildren(),role,false);	
		}
	}
	/**
	 * 通过角色获取权限明细列表
	 * @param role
	 * @return
	 */
	public List<AuthorDetail> getAuthorDetailByRole(Role role){
		List<Role_AuthorDetail> role_AuthorDetailList = role_AuthorDetailDao.getByColumn("role", role);
		List<AuthorDetail> authorDetailList = new LinkedList<AuthorDetail>();
		for(Role_AuthorDetail role_AuthorDetail : role_AuthorDetailList){
			authorDetailList.add(role_AuthorDetail.getAuthorDetail());
		}
		return authorDetailList;
	}
}
