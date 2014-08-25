package com.ZLHW.base.HTable;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;


public abstract class BasicModel implements Serializable{
	/**
	 * 获取主键值
	 */
	public abstract Serializable primary();
	/**
	 * 获取主键名称
	 */
	public abstract String primaryColumn();

	public String extendTableName(){
		return "BaseModel";
	}
	
	/**
	 * 判断两个对象是否相等
	 * @param tb
	 * @return
	 */
	public boolean equalWithByPrimary(BasicModel tb){
		if(tb==null)
			return false;
		if(tb.getClass()==this.getClass()&&this.primary().equals(tb.primary())){
			return true;
		}else{
			return false;
		}
	}

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
