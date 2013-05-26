package com.ZLHW.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum EnumAdminStatus {
	OFF(0,"离职"),
	ON(1,"上班");
	private int code;
	private String value;
	EnumAdminStatus(int code, String value) {
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
		for (EnumAdminStatus type : EnumAdminStatus.values()) {
			enumDataMap.put(type.getCode(), type.getValue());
        }
		return enumDataMap;
	}
	
	public static List toList(){
		List enumDataList = new ArrayList();
		for (EnumAdminStatus type : EnumAdminStatus.values()) {
			Map enumDataMap = new HashMap();
            enumDataMap.put("code", type.getCode());
            enumDataMap.put("value", type.getValue());
            enumDataList.add(enumDataMap);
        }
		return enumDataList;
	}
}
