package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.AdminUserGroup;
import com.zb.jnlxc.model.UserGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminUserGroupDAO extends DAO<AdminUserGroup,Integer> {
	private static final Log log = LogFactory.getLog(AdminUserGroupDAO.class);
}
