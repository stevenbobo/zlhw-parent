package com.zb.jnlxc.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zb.jnlxc.service.PaichanRecordService;
import net.sf.jasperreports.engine.JRException;

import org.dom4j.DocumentException;

import com.ZLHW.base.factory.BeanFactory;

public class PdfDownLoad extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/x-download");   
        response.setHeader("Content-disposition", "attachment;filename="
                + "a.pdf");
		String taskId=request.getParameter("taskId");
        PaichanRecordService service=(PaichanRecordService) BeanFactory.LookUp("PaichanRecordService");
		OutputStream out = response.getOutputStream();
		try {
			service.startCreatePDF(taskId,out);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
			out.flush();
	        out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
