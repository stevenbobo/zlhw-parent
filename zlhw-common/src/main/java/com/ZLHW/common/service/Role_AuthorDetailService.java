package com.ZLHW.common.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.Role_AuthorDetailDAO;
import com.ZLHW.common.model.AuthorDetail;
import com.ZLHW.common.model.Role;
import com.ZLHW.common.model.Role_AuthorDetail;
@Transactional
@Service("Role_AuthorDetailService")
public class Role_AuthorDetailService extends BaseService<Role_AuthorDetailDAO,Role_AuthorDetail, Integer> {
	private static final Log log = LogFactory.getLog(Role_AuthorDetailService.class);

	public void editRole_AuthorDetail(Role role,List<AuthorDetail> authorDetails) throws BaseErrorModel{
		//查询被删的明细
		String hql1="from Role_AuthorDetail t where t.role= ? and t.authorDetail not in(?)";
		List<Role_AuthorDetail> l1=this.getDao().findByHQL(hql1, role,authorDetails);
		for(Role_AuthorDetail role_AuthorDetail:l1){
			this.delete(role_AuthorDetail);
		}
		//查询不变的明细
		String hql2="from Role_AuthorDetail t where t.role= ? and t.authorDetail in(?)";
		List<Role_AuthorDetail> l2=this.getDao().findByHQL(hql2, role,authorDetails);
		for(AuthorDetail authorDetail:authorDetails){
			boolean newOne=true;
			for(Role_AuthorDetail role_AuthorDetail:l2){
				//如果不在表中则增加
				if(authorDetail.getDbId().equals(role_AuthorDetail.getAuthorDetail().getDbId())){
					newOne=false;
					break;
				}
			}
			if(newOne){
				Role_AuthorDetail ra=new Role_AuthorDetail();
				ra.setAuthorDetail(authorDetail);
				ra.setRole(role);
			}
		}
	}
}
