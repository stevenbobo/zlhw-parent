package com.zb.jnlxc.dao;
import java.util.List;
import javax.annotation.Resource;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.zb.jnlxc.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import com.ZLHW.base.dao.DAO;

@Component
public class ProductRecordDAO extends DAO<ProductRecord,Integer> {
	private static final Log log = LogFactory.getLog(ProductRecordDAO.class);
	@Resource
	private OrderDetailDAO orderDetailDAO;
    @Resource
    private ProductRecordDetailDAO productRecordDetailDAO;

	public List<ProductRecord> getProductRecordsByScheme(Integer schemeId){
		List<ProductRecord> list=findByHQL("from ProductRecord p where p.orderForm.scheme.id=?", schemeId);
		return list;
	}
	public List<ProductRecord> productList(Scheme scheme){
		String totalhql=" from ProductRecordDetail t where t.orderDetail.orderForm.scheme=?";
		return findByHQL(totalhql, scheme);
	}

    public List<ProductRecord> getProductListByOrderId(Integer orderId){
        String hql=" from ProductRecord t where t.orderForm.dbId=?";
        return findByHQL(hql, orderId);
    }
	
	public ProductRecord getById(Integer id){
        return super.getById(id);
	}

    public void delete(ProductRecord productRecord) throws BaseErrorModel {
        List<ProductRecordDetail> list=productRecordDetailDAO.findByHQL("from ProductRecordDetail p where p.productRecord=?", productRecord);
        int totalCompWeight=0;
        for(ProductRecordDetail pd:list){
            productRecordDetailDAO.delete(pd);
        }
        super.delete(productRecord);
    }

    public void deleteByOrderId(Integer orderId){
        List<ProductRecord> l =getProductListByOrderId(orderId);
        for(ProductRecord p:l){
            delete(p);
        }
    }
}
