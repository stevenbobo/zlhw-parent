package com.ZLHW.common.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.TaskExtDAO;
import com.ZLHW.common.model.TaskExt;
@Transactional
@Service("TaskExtService")
public class TaskExtService extends BaseService<TaskExtDAO,TaskExt, String> {
	private static final Log log = LogFactory.getLog(TaskExtService.class);

}
