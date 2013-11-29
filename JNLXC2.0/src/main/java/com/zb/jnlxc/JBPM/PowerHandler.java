package com.zb.jnlxc.JBPM;

import java.util.List;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.service.AdminService;

public class PowerHandler implements AssignmentHandler{
	private static final long serialVersionUID = 1L;

	  public String powerName;
	  public Integer powerId;

	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		  AdminService service=(AdminService)BeanFactory.LookUp("adminService");
		  List<Admin> list;
		  if(powerName!=null){
			  list=service.getPowerAdmins(powerName);
		  }else if(powerId!=null){
			  list=service.getPowerAdmins(powerId);
		  }else{
			  list=service.getPowerAdmins(execution.getActivity().getName());
		  }
		  for(Admin admin:list){
		  assignable.addCandidateUser(admin.getAccount());
		  }
	}
	  
}
