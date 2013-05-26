package com.ZLHW.common.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.JobDAO;
import com.ZLHW.common.dao.Organ_JobDAO;
import com.ZLHW.common.model.Job;
import com.ZLHW.common.model.Organ;
import com.ZLHW.common.model.Organ_Job;
@Transactional
@Service("Organ_JobService")
public class Organ_JobService extends BaseService<Organ_JobDAO,Organ_Job, Integer> {
	@Autowired
	private JobDAO jobDAO;
	private static final Log log = LogFactory.getLog(Organ_JobService.class);
	public void editJobs(Organ organ,List<Job> jobs) throws BaseErrorModel{
		//查询被删的岗位
		String hql1="from Organ_Job t where t.organ= ? and t.job not in(?)";
		List<Organ_Job> l1=this.getDao().findByHQL(hql1, organ,jobs);
		for(Organ_Job organ_Job:l1){
			this.delete(organ_Job);
		}
		//查询不变的岗位
		String hql2="from Organ_Job t where t.organ= ? and t.job in(?)";
		List<Organ_Job> l2=this.getDao().findByHQL(hql2, organ,jobs);
		for(Job job:jobs){
			boolean newOne=true;
			for(Organ_Job organ_Job:l2){
				//如果不在表中则增加
				if(job.getDbId().equals(organ_Job.getJob().getDbId())){
					newOne=false;
					break;
				}
			}
			if(newOne){
				Organ_Job oj=new Organ_Job();
				oj.setJob(job);
				oj.setOrgan(organ);
			}
		}
	}
}
