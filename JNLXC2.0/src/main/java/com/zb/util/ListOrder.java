package com.zb.util;

//用于倒序List的方法
import java.util.ArrayList;
import java.util.List;

public class ListOrder {
	private static ListOrder listOrder = null;
	private ListOrder(){
		
	}
	
	public static ListOrder getCreate(){
		if(null==listOrder){
			listOrder = new ListOrder();
		}		
		return listOrder;
	}
	
	public List order(List list){
		List order_list = new ArrayList();
		int size = list.size();
		for(int i = 0; i < size; i++){
			order_list.add(list.get(size-i-1));
		}
		return order_list;
	}
}
