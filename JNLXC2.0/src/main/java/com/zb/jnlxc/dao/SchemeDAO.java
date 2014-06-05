package com.zb.jnlxc.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zb.jnlxc.model.PaiChanRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.IDAO.IScheme;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.OrderForm;
import com.zb.jnlxc.model.Scheme;

@Component
public class SchemeDAO extends DAO<Scheme,Integer> implements IScheme{
	private static final Log log = LogFactory.getLog(SchemeDAO.class);

	public SchemeDAO() {
		super();
	}
	
	public List<OrderForm> getOrderFormBySchemeId(int schemeId) {
		List orderFormrList=findByHQL("from OrderForm where scheme.id=?", schemeId);
		Map map=new HashMap();
		map.put("list", orderFormrList);
		return orderFormrList;
	}
	
	public Map getSchemeByType(String type,int fromIdx, int fetchCount) {
		Map map=new HashMap();
		List list= findByHQLWithIndex("from Scheme where type=?", fromIdx, fetchCount, type);
		map.put("list", list);
		return map;
	}

	public int getSchemeNumByDesigner(String designerId) {
		Map map=new HashMap();
		int schemeNum=getCountOfAll("from Scheme where designerId=?", designerId);
		map.put("schemeNum", schemeNum);
		return schemeNum;
	}

	public List<PaiChanRecord> getProductRecordBySchemeId(int schemeId) {
		List productRecordList=findByHQL("from PaiChanRecord t where t.orderForm.scheme.id=?", schemeId);
		Map map=new HashMap();
		map.put("list", productRecordList);
		return productRecordList;
	}

	public List<Mould> getMouldBySchemeId(int schemeId) {
		List mouldList=findByHQL("from Mould where scheme.id=?", schemeId);
		Map map=new HashMap();
		map.put("list", mouldList);
		return mouldList;
	}
	
	public void delete(Scheme scheme) throws BaseErrorModel{
		//删除图纸前查找是否有模具记录
		int mouldNum=this.getCountOfAll("select count(*) from Mould m where m.scheme=?", scheme);
		if(mouldNum>0)
			throw new BaseErrorModel("请先删除相关模具记录","");
		//删除图纸前查找是否有订单记录
		int orderFormNum=this.getCountOfAll("select count(*) from OrderForm o where o.scheme=?", scheme);
		if(orderFormNum>0)
			throw new BaseErrorModel("请先删除相关订单记录","");
		
		super.delete(scheme);
	}

}
