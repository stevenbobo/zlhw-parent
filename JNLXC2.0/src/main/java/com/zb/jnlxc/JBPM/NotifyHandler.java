package com.zb.jnlxc.JBPM;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.service.AdminService;

public class NotifyHandler implements AssignmentHandler{
	private static final long serialVersionUID = 1L;


	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
	      String reciverAccount=(String) execution.getVariable("reciverAccount");
	      String reciverGroupName=(String) execution.getVariable("reciverGroupName");
		  AdminService adminService=(AdminService)BeanFactory.LookUp("adminService");
		  
          if(StringUtils.isNotBlank(reciverAccount)){
              Admin reciver = adminService.getByAccount(reciverAccount);
              assignable.setAssignee(reciver.getAccount());
              return;
          }
          
		  if(StringUtils.isNotBlank(reciverGroupName)){
		      List<Admin> list =adminService.getPowerAdmins(reciverGroupName);
		      for(Admin admin:list){
		          assignable.addCandidateUser(admin.getAccount());
	          }
		      return;
		  }
	}
}
