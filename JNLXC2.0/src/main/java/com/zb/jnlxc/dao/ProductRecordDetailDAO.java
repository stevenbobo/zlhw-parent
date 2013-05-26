package com.zb.jnlxc.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.OrderDetail;
import com.zb.jnlxc.model.ProductRecord;
import com.zb.jnlxc.model.ProductRecordDetail;

@Component
public class ProductRecordDetailDAO extends DAO<ProductRecordDetail,Integer> {
	private static final Log log = LogFactory.getLog(ProductRecordDetailDAO.class);

	/**
	 * 检查订单明细是否有对应的生产记录
	 * @param orderDetail
	 * @return 有记录返回true 否则false
	 */
	public boolean checkedByOrderDetail(OrderDetail orderDetail){
		int total=this.getCountOfAll("from ProductRecordDetail p where p.orderDetail=?", orderDetail);
		if(total>0)
			return true;
		else
			return false;
	}
	public List<ProductRecordDetail> getByProductRecord(ProductRecord productRecord){
		List<ProductRecordDetail> lists=this.findByHQL("from ProductRecordDetail p where p.productRecord=?", productRecord);
		return lists;
	}

    public ProductRecordDetail getByOrderDetail(OrderDetail orderDetail,ProductRecord productRecord){
        List<ProductRecordDetail> lists=this.findByHQL("from ProductRecordDetail p " +
                "where p.orderDetail=? and p.productRecord=?", orderDetail,productRecord);
        if(lists.size()>0)
            return lists.get(0);
        else
            return null;
    }

    /**
     *
     * @param orderDetail
     * @return
     */
    public Map<String,Long> getCompDetail(OrderDetail orderDetail){
        List l = this.findByHQL("select " +
                "new Map(sum(p.detailWeight) as compWeight," +
                "sum(p.detailQuantity) as compQuantity)" +
                "from ProductRecordDetail p where p.orderDetail=?", orderDetail);
        Map<String,Long> map = (Map<String, Long>) l.get(0);
        if(map.get("compWeight")==null){
            map.put("compWeight",0L);
        }
        if(map.get("compQuantity")==null){
            map.put("compQuantity",0L);
        }
        return map;
    }



}
