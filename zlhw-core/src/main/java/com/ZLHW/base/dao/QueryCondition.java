package com.ZLHW.base.dao;

public class QueryCondition {
	public QueryCondition(String name,Object content){
		this.name=name;
		this.content=content;
	}
	private String name;
	private Object content;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	
}
