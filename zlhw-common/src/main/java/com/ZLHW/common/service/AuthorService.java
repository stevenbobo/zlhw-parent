package com.ZLHW.common.service;

import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.AuthorDAO;
import com.ZLHW.common.dao.AuthorDetailDAO;
import com.ZLHW.common.model.Author;
@Transactional
@Service
public class AuthorService extends BaseService<AuthorDAO,Author, Integer> {
	@Autowired
	private AuthorDAO authorDao;
	@Autowired
	private AuthorDetailDAO authorDetailDao;
	
}
