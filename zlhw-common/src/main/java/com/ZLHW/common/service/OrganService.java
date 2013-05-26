package com.ZLHW.common.service;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.JobDAO;
import com.ZLHW.common.dao.OrganDAO;
import com.ZLHW.common.dao.Organ_JobDAO;
import com.ZLHW.common.model.Job;
import com.ZLHW.common.model.Organ;
import com.ZLHW.common.model.Organ_Job;
import com.ZLHW.common.model.TreeNode;

@Transactional
@Service("OrganService")
public class OrganService extends BaseService<OrganDAO,Organ, Integer> {
	private static final Log log = LogFactory.getLog(OrganService.class);
	@Autowired
	private OrganDAO organDao;
	@Autowired
	private JobDAO jobDao;
	@Autowired
	private Organ_JobDAO organ_JobDAO;
	
	public static final int ROOT_LEVEL = 1;
	
	public List<TreeNode> getOrganTree(){
		List<TreeNode> treeList = new LinkedList<TreeNode>();
		List<Organ> organList = this.findAll();
		for(Organ organ : organList){
			if(organ.getLever() == ROOT_LEVEL){
				TreeNode treeNode = new TreeNode();
				treeNode.setData(organ);
				treeNode.setText(organ.getName());
				treeList.add(treeNode);
				generateChildOrganTree(treeNode,organList);
			}
		}
		return treeList;
	}
	private void generateChildOrganTree(TreeNode treeNode,List<Organ> organList){
		if(treeNode.getChildren()==null)
			treeNode.setChildren(new LinkedList<TreeNode>());
		Organ organ = (Organ) treeNode.getData();
		for(Organ childOrgan : organList){
			if(organ.getDbId().intValue()==childOrgan.getParent().intValue()){
				TreeNode childNode = new TreeNode();
				childNode.setData(childOrgan);
				childNode.setText(childOrgan.getName());
				treeNode.getChildren().add(childNode);
				generateChildOrganTree(childNode,organList);
			}
		}
	}
	public List<Job> getJobByOrgan(Organ organ){
		List<Job> jobList = new LinkedList<Job>();
		for(Organ_Job organ_Job : organ_JobDAO.getByColumn("organ", organ)){
			jobList.add(organ_Job.getJob());
		}
		return jobList;
	}
	public List<TreeNode> getJobTreeByOrgan(Organ organ){
		List<TreeNode> treeList = new LinkedList<TreeNode>();
		List<Job> jobList = jobDao.findAll();
		List<Organ_Job> organ_JobList = organ_JobDAO.getByColumn("organ", organ);
		for(Job job : jobList){
			TreeNode treeNode = new TreeNode();
			treeList.add(treeNode);
			treeNode.setData(job);
			for(Organ_Job organ_Job : organ_JobList){
				if(organ_Job.getJob().equalWithByPrimary(job)){
					treeNode.setSelected(true);
					organ_JobList.remove(organ_Job);
					break;
				}
			}
		}
		return treeList;
	}
	public void disTributeJob(List<TreeNode> treeList,Organ organ){
		organ_JobDAO.deleteByColumn("organ", organ);
		for(TreeNode treeNode : treeList){
			if(treeNode.isSelected()){
				Organ_Job organ_Job = new Organ_Job();
				organ_Job.setJob((Job) treeNode.getData());
				organ_Job.setOrgan(organ);
				organ_JobDAO.create(organ_Job);
			}
		}
	}
	public Organ create(Organ organ) throws BaseErrorModel {
		if(organ.getParent()==null || organ.getParent()==0){
			organ.setLever(ROOT_LEVEL);
		}
		else{
			Organ parentOrgan = this.getById(organ.getParent());
			if(parentOrgan==null)
				throw new BaseErrorModel("数据错误，找不到父机构","");
			else{
				organ.setLever(parentOrgan.getLever()+1);
			}
		}
		return super.create(organ);
	}
}
