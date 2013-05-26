package com.zb.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Upload extends HttpServlet {


    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        request.setCharacterEncoding("utf-8");
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        fileUpload.setSizeMax(1024 * 1025 * 1024);

        try {
            List items = fileUpload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();
                } else {
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    System.out.println(fileName);
                    //如果文件以.dwg结尾
                    if(hasExt(fileName,"dwg"))
                    {
	                    String contentType = item.getContentType();
	                    boolean isInMemory = item.isInMemory();
	                    long sizeInBytes = item.getSize();
	                    String path = getServletContext().getRealPath("/")+"temp_map/";
	                    File uploadedFile = new File(path +fileName);
	                    
	                    if(uploadedFile.exists())
	                    	throw new IOException();
	                    	
	                    try {
							item.write(uploadedFile);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                    /*转换成位图
	                    String dwgFilePath=path+fileName;
	                    fileName = fileName.replaceAll("dwg", "bmp");
	                    String outFilePath=getServletContext().getRealPath("/")+"img/"+fileName;
	                    DwgToBitmap img=new DwgToBitmap(dwgFilePath,outFilePath,500,500);
	                    img.convertToBitmap();
	                    */
                    }
                    else
                    	throw new IOException();
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
    public static Boolean hasExt (String fileName,String ext)
    {
    	return fileName.substring(fileName.length()-ext.length()-1, fileName.length())
		    			.trim()
		    			.toLowerCase()
		    			.equals("."+ext.toLowerCase());
    }
}