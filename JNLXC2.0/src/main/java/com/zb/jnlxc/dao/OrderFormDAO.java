package com.zb.jnlxc.dao;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.OrderDetail;
import com.zb.jnlxc.model.OrderForm;

@Component
public class OrderFormDAO extends DAO<OrderForm,Integer> {
	private static final Log log = LogFactory.getLog(OrderFormDAO.class);
	@Resource
	private OrderDetailDAO orderDetailDAO;
    @Resource
    private ProductRecordDAO productRecordDAO;
    @Resource
    private ProductRecordDetailDAO productRecordDetailDAO;

	public OrderDetailDAO getOrderDetailDAO() {
		return orderDetailDAO;
	}

	public void setOrderDetailDAO(OrderDetailDAO orderDetailDAO) {
		this.orderDetailDAO = orderDetailDAO;
	}

	public void delete(OrderForm orderForm) throws BaseErrorModel{
	    this.refresh(orderForm);
        productRecordDAO.deleteByOrderId(orderForm.getDbId());
		List<OrderDetail> orderDetails = orderDetailDAO.findByHQL("from OrderDetail o where o.orderForm=?", orderForm);
		for(OrderDetail od:orderDetails){
			orderDetailDAO.delete(od);
		}
		super.delete(orderForm);
	}

    public void updateCompWeight(OrderForm orderForm){
        Map<String,Long> map = productRecordDetailDAO.getCompMapByOrder(orderForm);
        Long compWeight = map.get("compWeight");
        Long compQuantity = map.get("compQuantity");
        orderForm.setCompQuantity(compQuantity.intValue());
        orderForm.setCompWeight(compWeight.intValue());
        this.update(orderForm);

    }


	
	
}
