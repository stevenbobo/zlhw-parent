package com.zb.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PageService {


	private int currentPage = 1;// 表示当前请求的分页号
	
	private int numPerPage; // 每页显示的记录数

	private int totalCount; // 总条数
	
	public PageService(int currentPage,int numPerPage,int totalCount){
		this.currentPage=currentPage;
		this.numPerPage=numPerPage;
		this.totalCount=totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
