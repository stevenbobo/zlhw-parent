package com.zb.jnlxc.dao;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zb.jnlxc.model.PaiChanRecord;
import com.zb.jnlxc.model.PaichanOrderDetail;
import com.zb.util.Constants;
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
    private PaiChanRecordDAO paiChanRecordDAO;
    @Resource
    private PaichanOrderDetailDAO paichanOrderDetailDAO;

	public OrderDetailDAO getOrderDetailDAO() {
		return orderDetailDAO;
	}

	public void setOrderDetailDAO(OrderDetailDAO orderDetailDAO) {
		this.orderDetailDAO = orderDetailDAO;
	}

	public void delete(OrderForm orderForm) throws BaseErrorModel{
	    this.refresh(orderForm);
        paiChanRecordDAO.deleteByOrderId(orderForm.getDbId());
		List<OrderDetail> orderDetails = orderDetailDAO.findByHQL("from OrderDetail o where o.orderForm=?", orderForm);
		for(OrderDetail od:orderDetails){
			orderDetailDAO.delete(od);
		}
		super.delete(orderForm);
	}

    public void updateCompWeight(OrderForm orderForm){
        Map<String,Long> map = paichanOrderDetailDAO.getCompMapByOrder(orderForm);
        Long compWeight = map.get("compWeight");
        Long compQuantity = map.get("compQuantity");
        orderForm.setCompQuantity(compQuantity.intValue());
        orderForm.setCompWeight(compWeight.intValue());
        this.update(orderForm);
    }



    public void updateCompState(OrderForm orderForm) {
        boolean isFinished = true;
        List<OrderDetail> orderDetails = orderDetailDAO.getByOrderForm(orderForm);
        for(OrderDetail orderDetail:orderDetails){
            boolean orderDetailFinished = orderDetail.getCompQuantity()>=
                    orderDetail.getOrderQuantity();
            isFinished = isFinished&&orderDetailFinished;
            orderDetailDAO.updateCompState(orderDetail);
        }
        if(isFinished){
            orderForm.setCurrentState(Constants.ORDER_FINISHED);
            orderForm.setCompStatus((byte)1);
            this.update(orderForm);
        }
    }
}
