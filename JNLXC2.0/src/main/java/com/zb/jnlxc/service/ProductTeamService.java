package com.zb.jnlxc.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.ProductTeamDAO;
import com.zb.jnlxc.model.ProductTeam;

import java.util.List;

@Transactional
@Service
public class ProductTeamService extends BaseService<ProductTeamDAO,ProductTeam, Integer> {
	private static final Log log = LogFactory.getLog(ProductTeamService.class);

    public List findAllTeamDetail() {
        String hql = "select pt.dbId,pt.name,a.name as chargeName from ProductTeam pt,Admin a " +
                "where pt.depart.charge_dbId=a.dbId";
        return this.findHql(hql);
    }
}
