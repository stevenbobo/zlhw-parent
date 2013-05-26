package com.zb.jnlxc.service;
import java.util.Date;
import java.util.List;

import com.zb.jnlxc.dao.OrderDetailDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.model.OrderDetail;
@Transactional
@Service
public class OrderDetailService extends BaseService<OrderDetailDAO,OrderDetail, Integer>{
	/**
	 * 通过订货时间查询在该时间之前订货的所有未完成订单的未完成总数量
	 * @param date
	 * @return
	 */
	private String getUncompleteNum(Date date){
		List list=getDao().findByHQL("select (sum(od.quantity)-sum(od.compNum)) as uncompNum" +
				" from OrderDetail od where od.orderForm.orderDate <= ?", date);
		String str=(String) list.get(0);
		return str;
	}

}
