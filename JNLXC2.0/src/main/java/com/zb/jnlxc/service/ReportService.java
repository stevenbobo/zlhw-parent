package com.zb.jnlxc.service;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zb.jnlxc.dao.OrderDetailDAO;
import com.zb.jnlxc.model.*;
import net.sf.jasperreports.engine.JRException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.JasperReport.CreateReportPDF;

import javax.annotation.Resource;

@Service
public class ReportService extends BaseService{
    @Value("${jnlxc.imagesRoot}")
    private String imagesRoot;
    @Resource
    private OrderDetailDAO orderDetailDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 以流的方式输出
	 * @param paiChanRecord
	 * @param os
	 * @throws JRException
	 * @throws DocumentException 
	 */
	public void exportProductRecord(PaiChanRecord paiChanRecord,OutputStream os) throws JRException, DocumentException{
		OrderForm orderForm=paiChanRecord.getOrderForm();
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
		parameters.put("time", paiChanRecord.getCreateDate());
		parameters.put("schemeCode", paiChanRecord.getOrderForm().getScheme().getName());
		parameters.put("client", paiChanRecord.getOrderForm().getClient().getClientCode());
        parameters.put("wcomment",paiChanRecord.getOrderForm().getMcomment());
        if(paiChanRecord.getCharger()!=null)
		    parameters.put("Charger", paiChanRecord.getCharger().getName());
        else
            parameters.put("Charger", "");
		parameters.put("orderForm", orderForm);
		parameters.put("recordid", paiChanRecord.getCode());
		File file=new File(imagesRoot+orderForm.getScheme().getBmpSrc());
		if(file.exists())
			parameters.put("BaseDir", imagesRoot+orderForm.getScheme().getBmpSrc());
		else
			parameters.put("BaseDir", imagesRoot+"default.bmp");
		parameters.put("pack", orderForm.getPack().getName());
		parameters.put("surfaceProcess", orderForm.getSurfaceProcess().getName());
		parameters.put("material", orderForm.getMaterial().getName());
        if(orderForm.getKpm()!=null)
            parameters.put("kpm",orderForm.getKpm().toString());
        if(orderForm.getPreSendDate()!=null)
        parameters.put("preSendDate",sdf.format(orderForm.getPreSendDate()));
        new CreateReportPDF("WebappReport", "a.pdf", parameters, list).exportReportToPdfStream(os);
    }
}
