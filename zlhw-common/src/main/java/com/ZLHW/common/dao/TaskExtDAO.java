package com.ZLHW.common.dao;

import com.ZLHW.base.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.common.model.TaskExt;
@Repository
public class TaskExtDAO extends DAO<TaskExt,String> {
	private static final Log log = LogFactory.getLog(TaskExtDAO.class);

}
