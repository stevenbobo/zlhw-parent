package com.zb.jnlxc.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.AuthorDetailDAO;
import com.zb.jnlxc.model.AuthorDetail;
@Transactional
@Service
public class AuthorDetailService extends BaseService<AuthorDetailDAO,AuthorDetail, Integer> {
}
