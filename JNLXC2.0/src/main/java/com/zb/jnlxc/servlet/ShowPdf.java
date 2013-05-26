package com.zb.jnlxc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowPdf extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String taskId=request.getParameter("taskId");

		response.getWriter().write("<object type=\"application/pdf\" width=\"98%\" height=\"98%\"  >");
		response.getWriter().write("<param name=\"SRC\" value=\"./downloadPDF/show.do?taskId="+taskId+"\" />");
		response.getWriter().write("</object>");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
