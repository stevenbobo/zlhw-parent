package com.ZLHW.base.HTable;

public interface IExtendForFlex {
	/**
	 * 生成flexbean时候的继承名称
	 * @return
	 */
	public String extendTableName();
	/**
	 * 返回所有的FLEX GET和SET方法
	 */
	public abstract String printFLEXList();
}
