package com.zb.jnlxc.dao;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.IDAO.IMould;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.Scheme;

@Component
public class MouldDAO extends DAO<Mould,Integer> implements IMould{
	private static final Log log = LogFactory.getLog(MouldDAO.class);

	public MouldDAO() {
		super();
	}

	public Map getNumByStatus(String status, int fromIdx, int fetchCount) {
		Map map = new HashMap();
		List list = findByHQL(
				"select sum(preProNum), sum(factProNum) "
						+ "from Mould where status=?", fromIdx, fetchCount,
				status);
		map.put("list", list);
		return map;
	}

	public Map getMouldList(String status, String sortType, Date startDate,
			Date endDate, int fromIdx, int fetchCount) {
		Map map = new HashMap();
		List list = findByHQL(
				"from Mould where status=? and manuDate between ? and ? order by "
						+ sortType, fromIdx, fetchCount, status, startDate,
				endDate);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 查找该图纸的所有可用模具
	 * @param scheme
	 * @return
	 */
	public List<Mould> getUsedMouldListByScheme(Scheme scheme){
        return findByHQL(
				"from Mould m where m.scheme=? ",scheme);
	}


}
