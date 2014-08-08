package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.*;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhengbo.zb on 2014/6/1.
 */
@Component
public class RemaindProductDAO  extends DAO<RemaindProduct,Integer> {
    @Resource
    PaichanOrderDetailDAO paichanOrderDetailDAO ;
    @Resource
    OrderDetailDAO orderDetailDAO ;

    /**
     * 保存胚料记录
     * @param orderForm
     * @param paiChanRecord
     */
    public void storeRemainProduct(OrderForm orderForm,PaiChanRecord paiChanRecord){
        boolean isFinished = true;
        List<PaichanOrderDetail> paichanOrderDetailList = paichanOrderDetailDAO.findByPaichan(paiChanRecord.getDbId());
        for(PaichanOrderDetail paichanOrderDetail:paichanOrderDetailList){
            OrderDetail orderDetail = paichanOrderDetail.getOrderDetail();
            Integer remaindQuantity = orderDetail.getCompQuantity()
                    -orderDetail.getOrderQuantity();
            Integer remaindWeight = orderDetail.getCompWeight()
                    -orderDetail.getOrderWeight();
            if(remaindQuantity>0){
                RemaindProduct remaindProduct = new RemaindProduct();
                remaindProduct.setPaichanOrderDetail(paichanOrderDetail);
                remaindProduct.setCreateDate(new Date());
                remaindProduct.setRemaindQuantity(remaindQuantity);
                create(remaindProduct);
                orderDetail.setCompWeight(orderDetail.getOrderWeight());
                orderDetail.setCompQuantity(orderDetail.getCompQuantity());
                orderDetailDAO.update(orderDetail);
            }
        }
    }
}
