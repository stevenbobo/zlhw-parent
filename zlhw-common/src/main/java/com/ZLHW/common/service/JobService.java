package com.ZLHW.common.service;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.JobDAO;
import com.ZLHW.common.dao.Job_RoleDAO;
import com.ZLHW.common.dao.RoleDAO;
import com.ZLHW.common.model.Job;
import com.ZLHW.common.model.Job_Role;
import com.ZLHW.common.model.Role;
import com.ZLHW.common.model.TreeNode;
@Transactional
@Service("JobService")
public class JobService extends BaseService<JobDAO,Job, Integer> {
	private static final Log log = LogFactory.getLog(JobService.class);
	@Autowired
	private JobDAO jobDao;
	@Autowired
	private RoleDAO roleDao;
	@Autowired
	private Job_RoleDAO job_RoleDao;
	public List<Role> getRoleByJob(Job job){
		List<Role> roleList = new LinkedList<Role>();
		for(Job_Role job_Role : job_RoleDao.getByColumn("job", job)){
			roleList.add(job_Role.getRole());
		}
		return roleList;
	}
	
	public List<TreeNode> getRoleTreeByJob(Job job){
		List<Job_Role> job_RoleList = job_RoleDao.getByColumn("job", job);
		List<Role> roleList = roleDao.findAll();
		List<TreeNode> treeList = new LinkedList<TreeNode>();
		for(Role role : roleList){
			TreeNode treeNode = new TreeNode();
			treeNode.setData(role);
			treeList.add(treeNode);
			for(Job_Role job_Role : job_RoleList){
				if(job_Role.getRole().equalWithByPrimary(role)){
					treeNode.setSelected(true);
					job_RoleList.remove(job_Role);
					break;
				}
			}
		}
		return treeList;
	}
	
	public void disTributeRole(List<TreeNode> treeList,Job job){
		job_RoleDao.deleteByColumn("job", job);
		for(TreeNode node : treeList){
			if(node.isSelected()){
				Job_Role job_Role = new Job_Role();
				job_Role.setJob(job);
				job_Role.setRole((Role)node.getData());
				job_RoleDao.create(job_Role);
			}
		}
	}
}
