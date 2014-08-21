package com.zb.jnlxc.JBPM;

import java.util.List;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.service.AdminService;

public class NotifyHandler implements AssignmentHandler{
	private static final long serialVersionUID = 1L;


	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
	      Integer reciverId=(Integer) execution.getVariable("reciverId");
	      Integer reciverGroupId=(Integer) execution.getVariable("reciverGroupId");
		  AdminService adminService=(AdminService)BeanFactory.LookUp("adminService");
		  
          if(reciverId!=null){
              Admin reciver = adminService.getById(reciverId);
              assignable.setAssignee(reciver.getAccount());
              return;
          }
          
		  if(reciverGroupId!=null){
		      List<Admin> list =adminService.getPowerAdmins(reciverGroupId);
		      for(Admin admin:list){
		          assignable.addCandidateUser(admin.getAccount());
	          }
		      return;
		  }

		  
	}
}
