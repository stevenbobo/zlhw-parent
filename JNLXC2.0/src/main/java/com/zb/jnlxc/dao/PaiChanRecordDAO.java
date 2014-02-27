package com.zb.jnlxc.dao;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhengbo.zb
 * Date: 14-2-4
 * Time: 下午7:25
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PaiChanRecordDAO extends DAO<PaiChanRecord,Integer> {
    @Resource
    private PaiChanMouldDAO paiChanMouldDAO;
    @Resource
    private PaiChanOrderDAO paiChanOrderDAO;
    @Resource
    private OrderFormDAO orderFormDAO;
    @Resource
    private MouldDAO mouldDAO;

    /**
     * 排摸列表更新
     * @param paiChanRecord
     */
    public void mouldUpdate(PaiChanRecord paiChanRecord){
        String ids = paiChanRecord.getMouldIds();
        String[] mouldIds = ids.split(",");
        paiChanMouldDAO.updateHql("delete from PaiChanMould t where t.paiChanRecord = ?",paiChanRecord);

        for(String dbId:mouldIds){
            Mould mould = mouldDAO.loadById(Integer.parseInt(dbId));
            PaiChanMould paiChanMould = new PaiChanMould();
            paiChanMould.setMould(mould);
            paiChanMould.setPaiChanRecord(paiChanRecord);
            paiChanMouldDAO.create(paiChanMould);
        }
    }

    /**
     * 排产列表更新
     * @param paiChanRecord
     */
    public void paiChanUpdate(PaiChanRecord paiChanRecord){
        paiChanMouldDAO.updateHql("delete from PaiChanOrder t where t.paiChanRecord = ?",paiChanRecord);
        updatePaiChanOrder(paiChanRecord);
    }

    /**
     * 第一次创建排产
     * @param paiChanRecord
     * @return
     */
    public PaiChanRecord create(PaiChanRecord paiChanRecord){
        super.create(paiChanRecord);
        updatePaiChanOrder(paiChanRecord);
        return paiChanRecord;
    }

    private void updatePaiChanOrder(PaiChanRecord paiChanRecord){
        String ids = paiChanRecord.getOrderIds();
        String[] orderIds = ids.split(",");
        for(String id :orderIds){
            OrderForm orderForm = orderFormDAO.loadById(Integer.parseInt(id));

            if(orderIds.length>=1){
                //合并订单不允许有重复订单排产
                List<PaiChanOrder> list = paiChanOrderDAO.findByHQL("from PaiChanOrder t where t.orderForm.dbId=? "
                        ,orderForm.getDbId());
                if(list.size()>0)
                    throw new BaseErrorModel("订单:"+orderForm.getCode()+"已排产","");
            }
            PaiChanOrder paiChanOrder = new PaiChanOrder();
            paiChanOrder.setOrderForm(orderForm);
            paiChanOrder.setPaiChanRecord(paiChanRecord);
            paiChanOrderDAO.create(paiChanOrder);
        }
    }
}
