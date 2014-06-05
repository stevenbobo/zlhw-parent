package com.zb.jnlxc.dao;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.OrderDetail;
import com.zb.jnlxc.model.OrderForm;

import javax.annotation.Resource;

@Component
public class OrderDetailDAO extends DAO<OrderDetail,Integer> {
	private static final Log log = LogFactory.getLog(OrderDetailDAO.class);

    @Resource
    private PaichanOrderDetailDAO paichanOrderDetailDAO;

	public void delete(OrderDetail orderDetail) throws BaseErrorModel{
		//删除图纸前查找是否有订单记录
		int num=this.getCountOfAll("select count(*) from ProductRecordDetail o where o.orderDetail=?", orderDetail);
		if(num>0)
			throw new BaseErrorModel("请先删除相关生产记录","");
		super.delete(orderDetail);
	}
	
	public List<OrderDetail> getByOrderForm(OrderForm orderForm) throws BaseErrorModel{
		//删除图纸前查找是否有订单记录
		return this.findByHQL("from OrderDetail o where o.orderForm=?", orderForm);
	}

    public List<OrderDetail> getByScheme(Integer schemeId) throws BaseErrorModel{
        //删除图纸前查找是否有订单记录
        return this.findByHQL("from OrderDetail o where o.orderForm.scheme.dbId=?", schemeId);
    }

    public void updateCompWeight(OrderDetail od) {
        Map<String,Long> map = paichanOrderDetailDAO.getCompDetail(od);
        Long compWeight = map.get("compWeight");
        Long compQuantity = map.get("compQuantity");
        od.setCompQuantity(compQuantity.intValue());
        od.setCompWeight(compWeight.intValue());
        this.update(od);
    }

    public void updateCompState(OrderDetail od) {
        Map<String,Long> map = paichanOrderDetailDAO.getCompDetail(od);
        Long compWeight = map.get("compWeight");
        Long compQuantity = map.get("compQuantity");
        od.setCompQuantity(compQuantity.intValue());
        od.setCompWeight(compWeight.intValue());
        if(compQuantity>=od.getOrderQuantity()){
            od.setCompStatus((byte)1);
        }
        this.update(od);
    }

    public void updatePaichanWeight(OrderDetail od) {
        Map<String,Long> map = paichanOrderDetailDAO.getPaichanDetail(od);
        Long orderWeight = map.get("orderWeight");
        Long orderQuantity = map.get("orderQuantity");
        od.setPaichanQuantity(orderQuantity.intValue());
        od.setPaichanWeight(orderWeight.intValue());
        this.update(od);
    }
}
