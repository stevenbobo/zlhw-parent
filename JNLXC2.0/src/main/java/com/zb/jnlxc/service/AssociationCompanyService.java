package com.zb.jnlxc.service;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.AssociationCompanyDAO;
import com.zb.jnlxc.model.AssociationCompany;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AssociationCompanyService extends BaseService<AssociationCompanyDAO,AssociationCompany, Integer> {
	private static final Log log = LogFactory.getLog(AssociationCompanyService.class);
    public Page load(Page page){
        StringBuffer hql = new StringBuffer("from AssociationCompany t where 1=1 ");
        List<QueryCondition> queryConditions=new ArrayList();
        if(StringUtils.isNotEmpty(page.getParameter("name"))){
            String clentName = page.getParameter("name");
            hql.append("and t.name like :clientName ");
            queryConditions.add(new QueryCondition("clientName", "%"+clentName+"%"));
        }

        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        return this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
    }
}
