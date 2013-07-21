package com.zb.jnlxc.JBPM;

import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.service.AdminService;
import com.zb.jnlxc.service.ProductTeamService;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.model.ProductTeam;
import com.zb.jnlxc.service.BusinessService;

import java.util.List;

public class PrintProductAssignTask implements AssignmentHandler{
	public void assign(Assignable assignable, OpenExecution execution) {
        AdminService adminService=(AdminService)BeanFactory.LookUp("adminService");
        ProductTeamService productTeamService=(ProductTeamService)BeanFactory.LookUp("productTeamService");
        Integer productTeamId = (Integer)execution.getVariable("productTeamId");
		ProductTeam team=productTeamService.getById(productTeamId);
        List<Admin> list=adminService.getPowerAdmins("机台负责人打印生产跟踪单");
        for(Admin admin:list){
            assignable.addCandidateUser(admin.getAccount());
        }
	    assignable.addCandidateUser(adminService.loadById(team.getCharge_dbId()).getAccount());
	  }
}
