package com.ZLHW.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public enum EnumAuthorDetailStatus {
	SHOW(0,"菜单可见"),
	HIDE(1,"菜单不可见");
	private int code;
	private String value;
	EnumAuthorDetailStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public static Map<Integer, String> toMap(){
		Map<Integer, String> enumDataMap = new HashMap<Integer, String>();
		for (EnumAuthorDetailStatus type : EnumAuthorDetailStatus.values()) {
			enumDataMap.put(type.getCode(), type.getValue());
        }
		return enumDataMap;
	}
	
	public static List toList(){
		List enumDataList = new ArrayList();
		for (EnumAuthorDetailStatus type : EnumAuthorDetailStatus.values()) {
			Map enumDataMap = new HashMap();
            enumDataMap.put("code", type.getCode());
            enumDataMap.put("value", type.getValue());
            enumDataList.add(enumDataMap);
        }
		return enumDataList;
	}
	
}
