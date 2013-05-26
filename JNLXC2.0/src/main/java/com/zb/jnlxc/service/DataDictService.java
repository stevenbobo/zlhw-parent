package com.zb.jnlxc.service;

import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.DataDictDAO;
import com.zb.jnlxc.model.DataDict;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DataDictService extends BaseService<DataDictDAO,DataDict, Integer> {
	private static final Log log = LogFactory.getLog(DataDictService.class);
	
	/**
	 * 根据目录编号获取字典
	 * @param code
	 * @return
	 */
	public List<DataDict> loadDictList(String code){
		return this.findHql("from DataDict t where t.dataDictCatalog.code=?", code);
	}
    public Page loadDictList(Page page,String code){
        StringBuilder hql=new StringBuilder("from DataDict t where t.dataDictCatalog.code=? ");
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        return this.findByPageWithHQL(page,hql.toString(),code);
    }

    public void update(DataDict dataDict){
        if(dataDict.getEditDate()==null){
            dataDict.setEditDate(new Date());
        }
        super.update(dataDict);
    }

    public DataDict findLastLvDing(){
        List<DataDict> l =this.findHql("from DataDict t where t.dataDictCatalog.code=? order by dbId desc",0,1, "lvdingPrice");
        if(l.size()>0)
            return l.get(0);
        else
            return null;
    }

}
