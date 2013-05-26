package com.zb.jnlxc.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.UserGroupDAO;
import com.zb.jnlxc.model.UserGroup;
@Transactional
@Service
public class UserGroupService extends BaseService<UserGroupDAO,UserGroup, Integer> {
	private static final Log log = LogFactory.getLog(UserGroupService.class);

}
