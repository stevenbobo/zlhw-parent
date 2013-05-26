package com.zb.jnlxc.IDAO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ZLHW.base.dao.IDAO;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.Scheme;


public interface IMould extends IDAO<Mould, Integer>{
	/**
	 * 根据模具当前状态统计数量和总质量
	 */
	Map getNumByStatus(String status,int fromIdx, int fetchCount);
	
	/**
	 * 根据状态，排列方式，起止日期输出模具清单
	 */
	Map getMouldList(String status, String sortType, Date startDate,
			Date endDate, int fromIdx, int fetchCount);
	/**
	 * 查找该图纸的所有可用模具
	 * @param scheme
	 * @return
	 */
	List getUsedMouldListByScheme(Scheme scheme);
}
