package com.zb.jnlxc.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.UserGroup_AuthorDAO;
import com.zb.jnlxc.model.UserGroup_Author;
@Transactional
@Service
public class UserGroup_AuthorService extends BaseService<UserGroup_AuthorDAO,UserGroup_Author, Integer> {
	private static final Log log = LogFactory.getLog(UserGroup_AuthorService.class);

}
