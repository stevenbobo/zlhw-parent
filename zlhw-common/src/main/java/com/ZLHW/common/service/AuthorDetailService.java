package com.ZLHW.common.service;
import java.util.List;
import java.util.Map;


import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.AuthorDetailDAO;
import com.ZLHW.common.enums.EnumAuthorDetailStatus;
import com.ZLHW.common.model.AuthorDetail;
@Transactional
@Service
public class AuthorDetailService extends BaseService<AuthorDetailDAO,AuthorDetail, Integer> {
	@Autowired
	private AuthorDetailDAO authorDetailDao;
	public static void main(String[] args) {
		AuthorDetailService a = (AuthorDetailService) BeanFactory.LookUp("AuthorDetailService");
	}
	public List getAuthorDetailStatusList(){
		return EnumAuthorDetailStatus.toList();
	}
	public Map getAuthorDetailStatusMap(){
		return EnumAuthorDetailStatus.toMap();
	}
}
