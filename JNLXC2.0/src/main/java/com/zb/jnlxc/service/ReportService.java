package com.zb.jnlxc.service;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zb.jnlxc.dao.OrderDetailDAO;
import net.sf.jasperreports.engine.JRException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.JasperReport.CreateReportPDF;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.OrderDetail;
import com.zb.jnlxc.model.OrderForm;
import com.zb.jnlxc.model.ProductRecord;
import com.zb.jnlxc.model.ProductTeam;

import javax.annotation.Resource;

@Service
public class ReportService extends BaseService{
    @Value("${jnlxc.imagesRoot}")
    private String imagesRoot;
    @Resource
    private OrderDetailDAO orderDetailDAO;
	/**
	 * 以流的方式输出
	 * @param productRecord
	 * @param os
	 * @throws JRException
	 * @throws DocumentException 
	 */
	public void exportProductRecord(ProductRecord productRecord,OutputStream os) throws JRException, DocumentException{
		OrderForm orderForm=productRecord.getOrderForm();
		List<OrderDetail> orderFormDetails=orderDetailDAO.getByOrderForm(orderForm);
		List list=new ArrayList();
		for(OrderDetail orderDetail:orderFormDetails){
			list.add(orderDetail);
		}
        while (list.size()<13){
            list.add(new OrderDetail());
        }
		Map parameters = new HashMap();
		parameters.put("ReportTitle", "生产跟踪单");
		parameters.put("time", productRecord.getCreateDate());
		parameters.put("schemeCode", productRecord.getOrderForm().getScheme().getCode());
		parameters.put("client", productRecord.getOrderForm().getClient().getClientCode());
        parameters.put("wcomment",productRecord.getOrderForm().getMcomment());
		parameters.put("Charger", productRecord.getCharger().getName());
		parameters.put("orderForm", orderForm);
		parameters.put("recordid", productRecord.getCode());
		File file=new File(imagesRoot+orderForm.getScheme().getBmpSrc());
		if(file.exists())
			parameters.put("BaseDir", imagesRoot+orderForm.getScheme().getBmpSrc());
		else
			parameters.put("BaseDir", imagesRoot+"default.bmp");
		parameters.put("pack", orderForm.getPack().getName());
		parameters.put("surfaceProcess", orderForm.getSurfaceProcess().getName());
		parameters.put("material", orderForm.getMaterial().getName());
		new CreateReportPDF("WebappReport", "a.pdf", parameters, list).exportReportToPdfStream(os);
	}
}
