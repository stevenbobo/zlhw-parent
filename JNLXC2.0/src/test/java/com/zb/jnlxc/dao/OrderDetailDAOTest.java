package com.zb.jnlxc.dao;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.OrderDetail;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zb.jnlxc.model.OrderForm;

import java.util.List;

@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class OrderDetailDAOTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Resource
	OrderDetailDAO orderDetailDAO;
    @Resource
    OrderFormDAO orderFormDAO;
    @Resource
    AdminDAO adminDAO;
	@Test
	public void testGetByOrderForm() {
//        OrderForm orderForm = orderFormDAO.loadById(39);
//        List<OrderDetail> list = orderDetailDAO.getByOrderForm(orderForm);
        List l =  adminDAO.findByHQL("select a from Admin a where a.userGroup.name=?","业务员");
        System.out.println("--------------------------------");
        System.out.println(new Gson().toJson(l));
	}


}
