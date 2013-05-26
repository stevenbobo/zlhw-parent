package com.zb.jnlxc.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zb.jnlxc.dao.AdminDAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.OrderDetail;
import com.zb.jnlxc.model.OrderForm;
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class OrderDetailServiceTest extends AbstractJUnit4SpringContextTests{
	@Resource
	OrderDetailService OrderDetailService;
	@Resource
	OrderFormService OrderFormService;
	@Resource
	AdminService adminService;
	
	@Resource
	BusinessService businessService;
	
//	@Test
	public void testCreate() {
		OrderForm orderForm = new OrderForm();
		orderForm.setDbId(0);
		Admin zql = adminService.getById(1);
		orderForm.setKaidan(zql);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setDbId(0);
		orderDetail.setOrderWeight(0);
		List set = new ArrayList();
		set.add(orderDetail);
		businessService.saveOrder(orderForm,set,zql);
//		OrderDetailService.create(orderDetail);
	}

}
