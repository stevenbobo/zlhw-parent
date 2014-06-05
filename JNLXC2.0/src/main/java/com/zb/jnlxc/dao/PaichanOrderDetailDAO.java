package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.OrderDetail;
import com.zb.jnlxc.model.OrderForm;
import com.zb.jnlxc.model.PaichanOrderDetail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengbo.zb on 2014/5/23.
 */
@Component
public class PaichanOrderDetailDAO extends DAO<PaichanOrderDetail,Integer> {

    public List<PaichanOrderDetail> findByPaichan(Integer paichanId){
        return findByHQL("from PaichanOrderDetail t where t.paichanRecord.dbId = ?",paichanId);
    }

    public PaichanOrderDetail getByOrderDetail(Integer orderDetailId,Integer paichanId) {
        List<PaichanOrderDetail> l = findByHQL("from PaichanOrderDetail t where t.orderDetail.dbId = ?" +
                " and t.paichanRecord.dbId = ?",orderDetailId,paichanId);
        if(l.size()>0)
            return l.get(0);
        else
            return null;
    }

    public List<PaichanOrderDetail> findByOrderForm(Integer orderId){
        return findByHQL("from PaichanOrderDetail t where t.orderDetail.orderForm.dbId = ?",orderId);
    }

     /**
     * @param orderDetail
     * @return
     */
    public Map<String,Long> getCompDetail(OrderDetail orderDetail){
        List l = this.findByHQL("select " +
                "new Map(sum(p.compWeight) as compWeight," +
                "sum(p.compQuantity) as compQuantity)" +
                "from PaichanOrderDetail p where p.orderDetail=?", orderDetail);
        Map<String,Long> map = (Map<String, Long>) l.get(0);
        if(map.get("compWeight")==null){
            map.put("compWeight",0L);
        }
        if(map.get("compQuantity")==null){
            map.put("compQuantity",0L);
        }
        return map;
    }

        /**
     *   获取订单完成情况
     * @param orderForm
     * @return
     */
    public Map<String,Long> getCompMapByOrder(OrderForm orderForm){
        List l = this.findByHQL("select " +
                "new Map(sum(p.compWeight) as compWeight," +
                "sum(p.compQuantity) as compQuantity)" +
                "from PaichanOrderDetail p where p.orderDetail.orderForm=?", orderForm);
        Map<String,Long> map = (Map<String, Long>) l.get(0);
        if(map.get("compWeight")==null){
            map.put("compWeight",0L);
        }
        if(map.get("compQuantity")==null){
            map.put("compQuantity",0L);
        }
        return map;
    }


    /**
     * @param orderDetail
     * @return
     */
    public Map<String,Long> getPaichanDetail(OrderDetail orderDetail){
        List l = this.findByHQL("select " +
                "new Map(sum(p.orderWeight) as orderWeight," +
                "sum(p.orderQuantity) as orderQuantity)" +
                "from PaichanOrderDetail p where p.orderDetail=?", orderDetail);
        Map<String,Long> map = (Map<String, Long>) l.get(0);
        if(map.get("orderWeight")==null){
            map.put("orderWeight",0L);
        }
        if(map.get("orderQuantity")==null){
            map.put("orderQuantity",0L);
        }
        return map;
    }
}
