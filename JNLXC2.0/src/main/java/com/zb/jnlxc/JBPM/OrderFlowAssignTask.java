package com.zb.jnlxc.JBPM;

import java.util.List;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.service.AdminService;

public class OrderFlowAssignTask implements AssignmentHandler{
	private static final long serialVersionUID = 1L;

	public int powerId;

	  public void assign(Assignable assignable, OpenExecution execution) {
		  AdminService service=(AdminService)BeanFactory.LookUp("adminService");
		  List<Admin> list=service.getPowerAdmins(powerId);
		  String moulder=(String) execution.getVariable("moulder");
		  if(moulder==null){
			  for(Admin admin:list){
				  assignable.addCandidateUser(admin.getAccount());
			}
		  }
		  else
			  assignable.setAssignee(moulder);
	  }
}
