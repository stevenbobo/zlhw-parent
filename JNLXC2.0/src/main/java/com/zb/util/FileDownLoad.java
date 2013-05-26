package com.zb.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public  class FileDownLoad {
	private HttpServletResponse response;
	public FileDownLoad(HttpServletResponse response){
		this.response=response;
	}
    public void DownLoad(String name,File file) throws IOException{
    	byte[] buf = new byte[1024];     
		int len = 0;     
	    BufferedInputStream br = null;     
		OutputStream ut = null;     
		response.reset();//必须加，不然保存不了临时文件     
		response.setContentType("application/x-msdownload");     
		response.setHeader("Content-Disposition","attachment; filename=" + name);     
	    br = new BufferedInputStream(new FileInputStream(file));     
		ut = response.getOutputStream();     
		while((len=br.read(buf))!=-1){     
		ut.write(buf, 0, len);     
	}
    }
}
