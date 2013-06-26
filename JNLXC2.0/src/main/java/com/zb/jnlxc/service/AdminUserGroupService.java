package com.zb.jnlxc.service;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.AdminDAO;
import com.zb.jnlxc.dao.AdminUserGroupDAO;
import com.zb.jnlxc.dao.UserGroupDAO;
import com.zb.jnlxc.dao.UserGroup_AuthorDAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.AdminUserGroup;
import com.zb.jnlxc.model.UserGroup;
import com.zb.jnlxc.model.UserGroup_Author;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class AdminUserGroupService extends BaseService<AdminUserGroupDAO,AdminUserGroup, Integer> {
	private static final Log log = LogFactory.getLog(AdminUserGroupService.class);
    @Resource
    private UserGroupDAO userGroupDAO;
    @Resource
    private AdminDAO adminDAO;
    public void createForAdmin(Admin admin) {
        String[] ugs = admin.getUserGroups().split(",");
        this.getDao().updateHql("delete from AdminUserGroup t where t.admin=?",admin);
        byte lever = 10;
        for(String i:ugs){
            Integer ugId = Integer.parseInt(i);
            AdminUserGroup adminUserGroup = new AdminUserGroup();
            adminUserGroup.setAdmin(admin);
            UserGroup userGroup = userGroupDAO.loadById(ugId);
            if(userGroup.getGroupLever() < lever)
                lever = userGroup.getGroupLever();
            adminUserGroup.setUserGroup(userGroupDAO.loadById(ugId));
            this.create(adminUserGroup);
        }
        admin.setLever(lever);
        adminDAO.update(admin);
    }


    public void deleteForAdmin(Integer adminDbId) {
        this.getDao().updateHql("delete from AdminUserGroup t where t.admin.dbId=?",adminDbId);
    }
}
