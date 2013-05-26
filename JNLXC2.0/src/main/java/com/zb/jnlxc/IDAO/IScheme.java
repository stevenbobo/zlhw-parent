package com.zb.jnlxc.IDAO;

import java.util.List;
import java.util.Map;

import com.ZLHW.base.dao.IDAO;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.OrderForm;
import com.zb.jnlxc.model.ProductRecord;
import com.zb.jnlxc.model.Scheme;

public interface IScheme extends IDAO<Scheme, Integer>{	
	/**
	 * 根据类型得到图纸列表
	 */
	Map getSchemeByType(String type,int fromIdx, int fetchCount);

	/**
	 * 根据设计人ID得到图纸数量
	 */
	int getSchemeNumByDesigner(String designerId);
	
	/**
	 * 根据图纸id得到模具列表
	 */
	List<Mould> getMouldBySchemeId(int schemeId);
	
	/**
	 * 根据图纸id得到生产单列表
	 */
	List<OrderForm> getOrderFormBySchemeId(int schemeId);
	
	/**
	 * 根据图纸id得到工作单列表
	 */
	List<ProductRecord> getProductRecordBySchemeId(int schemeId);
}
