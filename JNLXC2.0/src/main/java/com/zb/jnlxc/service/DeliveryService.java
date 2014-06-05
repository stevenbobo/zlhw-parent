package com.zb.jnlxc.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zb.jnlxc.dao.PaiChanRecordDAO;
import com.zb.jnlxc.dao.SchemeDAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.PaiChanRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.DeliveryDAO;
import com.zb.jnlxc.model.Delivery;
import com.zb.jnlxc.model.Scheme;
@Transactional
@Service
public class DeliveryService  extends BaseService<DeliveryDAO,Delivery, Integer> {
    @Resource
    private SchemeDAO schemeDAO;
	@Resource
	private PaiChanRecordDAO paiChanRecordDAO;
	public Delivery create(Delivery entity,Integer schemeId,Admin user) throws BaseErrorModel {
		entity.setDeliver(user);
		entity.setDeliveryDate(new Date());
		entity.setDbId(null);
        entity.setScheme(schemeDAO.loadById(schemeId));
		return super.create(entity);
	}
	
	public void startDeliveryFlow(Delivery entity) throws BaseErrorModel{
		Scheme scheme=entity.getScheme();
		String totalhql="select sum(t.detailWeight) from ProductRecordDetail t where t.orderDetail.orderForm.scheme=?";
		List l= this.getDao().findByHQL(totalhql, scheme);
		Integer total=(Integer) l.get(0);
		String deliveryhql="select sum(t.deliveryWeight) from Delivery t where t.scheme=?";
		List l2= this.getDao().findByHQL(deliveryhql, scheme);
		Integer deliveryWeight=(Integer) l.get(0);
		if(deliveryWeight>total){
			throw new BaseErrorModel("暂无存货","");
		}
		this.create(entity);
	}
	
	public List getAllSize(Scheme scheme){
		List l=this.getDao().findByHQL("select distinct(t.setSize) from OrderDetail t where t.orderForm.scheme=?", scheme);
		return l;
	}

	
	public List<PaiChanRecord> productList(Scheme scheme){
		return paiChanRecordDAO.productList(scheme);
	}
	
	public List<Delivery> getDeliveryBySchemeId(Integer schemeId){
		String deliveryhql=" from Delivery t where t.scheme.dbId=? order by t.id desc";
		return this.getDao().findByHQL(deliveryhql, schemeId);
	}

	
}
