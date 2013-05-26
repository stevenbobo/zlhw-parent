package com.ZLHW.base.Form;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class Page {

	private boolean aotuSort = true; // 自动排序 默认开启

	private String sortKey  = "dbId"; //默认排序关键字为 id

    private String sortOrder  = "desc"; //true 代表升序排列, false代表降序排列 ，默认为升序

	private int pageSize=10;//每页显示行数
	
	private int totalPage=0;//总页数
	
	private int currentPage=0;//当前页
	
	private int totalClum=0;//总记录数
	
	private Map<String,String> queryCondition;//查询条件
	
	private List resultData;//查询结果

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalClum() {
		return totalClum;
	}

	public void setTotalClum(int totalClum) {
		this.totalClum = totalClum;
	}

	public Map<String,String> getQueryCondition() {
		return queryCondition;
	}

    public String getParameter(String key){
       if(queryCondition==null)
           return null;
       else
           return queryCondition.get(key);
    }

	public void setQueryCondition(Map queryCondition) {
		this.queryCondition = queryCondition;
	}

	public List getResultData() {
		return resultData;
	}

	public void setResultData(List resultData) {
		this.resultData = resultData;
	}

	public boolean isAotuSort() {
		return aotuSort;
	}

	public void setAotuSort(boolean aotuSort) {
		this.aotuSort = aotuSort;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        if(StringUtils.isNotBlank(sortOrder))
            this.sortOrder = sortOrder;
    }

    public int getFromIdx(){
		return currentPage*pageSize-pageSize;
	}
	public int getEndIdx(){
		return currentPage*pageSize;
	}
}
