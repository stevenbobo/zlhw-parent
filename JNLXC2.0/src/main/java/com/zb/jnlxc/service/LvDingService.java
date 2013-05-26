package com.zb.jnlxc.service;

import java.sql.Date;
import java.util.List;

import com.zb.jnlxc.dao.DataDictDAO;
import com.zb.jnlxc.model.DataDict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
@Service
@Transactional
public class LvDingService extends BaseService<DataDictDAO,DataDict, Integer>{
	public void setAlPrice(String price) throws BaseErrorModel{
		DataDict todayAlPrice = getAlPrice();
		if(todayAlPrice == null){
			todayAlPrice = new DataDict();
			todayAlPrice.setName("铝锭价格");
			todayAlPrice.setEditDate(new Date(System.currentTimeMillis()));
			todayAlPrice.setName(price);
			this.create(todayAlPrice);
		}else{
			todayAlPrice.setName(price);
			this.update(todayAlPrice);
		}
	}	
	public DataDict getAlPriceByEditDate(Date editDate){
		List<DataDict> dataDictList = this.findHql(" from DataDict d where d.name = ? and d.editDate = ?", "铝锭价格",editDate);
		return dataDictList == null || dataDictList.size()<1 ? null : dataDictList.get(0);
	}
	public DataDict getAlPrice(){
		List<DataDict> dataDictList = this.findHql(" from DataDict d where d.name = ? and d.editDate = ?", "铝锭价格",new Date(System.currentTimeMillis()));
		return dataDictList == null || dataDictList.size()<1 ? null : dataDictList.get(0);
	}
	public List<DataDict> getAlPriceHistory(){
		List<DataDict> dataDictList = this.findHql(" from DataDict d where d.name = ?", "铝锭价格");
		return dataDictList;
	}
	public Page getAlPriceByPage(Page page){
		return this.findByPageWithHQL(page, "from DataDict t where t.name=? order by t.editDate desc", "铝锭价格");
	}
}
