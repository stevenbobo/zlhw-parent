package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private MouldDAO mouldDAO;
    @Resource
    private PaiChanRecordStatisticsDAO paiChanRecordStatisticsDAO;

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
            paiChanMould.setHasJiYa(false);
            paiChanMouldDAO.create(paiChanMould);
        }
    }

    /**
     * 排产列表更新
     * @param paiChanRecord
     */
    public void paiChanUpdate(PaiChanRecord paiChanRecord){
        paiChanMouldDAO.updateHql("delete from PaiChanOrder t where t.paiChanRecord = ?",paiChanRecord);
    }

    /**
     * 第一次创建排产
     * @param paiChanRecord
     * @return
     */
    public PaiChanRecord create(PaiChanRecord paiChanRecord){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        Integer num = paiChanRecordStatisticsDAO.getNextPaiChanRecordNum();
        String code = paiChanRecord.getOrderForm().getCode()+"-"+date+"-"+num;
        paiChanRecord.setCode(code);
        super.create(paiChanRecord);
        paiChanRecordStatisticsDAO.newPaiChanRecord();
        return paiChanRecord;
    }


    /**
     * 检验一个排产记录的所有模具排产是否通过挤压流程
     * @param paiChanRecord
     * @return
     */
    public boolean checkJiYaFinished(PaiChanRecord paiChanRecord) {
        boolean finished = true;
        List<PaiChanMould> list= paiChanMouldDAO.findByHQL("from PaiChanMould t where t.paiChanRecord = ?",paiChanRecord);
        for(PaiChanMould paiChanMould:list){
            finished = finished&&paiChanMould.getHasJiYa();
        }
        return finished;
    }

    public List<PaiChanMould> findPaiChanMoulds(PaiChanRecord paiChanRecord){
        List<PaiChanMould> list= paiChanMouldDAO.findByHQL("from PaiChanMould t where t.paiChanRecord = ?",paiChanRecord);
        return list;
    }

    public List<PaiChanRecord> productList(Scheme scheme) {
		String totalhql=" from PaichanOrderDetail t where t.orderDetail.orderForm.scheme=?";
		return findByHQL(totalhql, scheme);
	}


    public void deleteByOrderId(Integer orderId){
        List<PaiChanRecord> l =getProductListByOrderId(orderId);
        for(PaiChanRecord p:l){
            delete(p);
        }
    }

    public List<PaiChanRecord> getProductListByOrderId(Integer orderId){
        String hql=" from PaiChanRecord t where t.orderForm.dbId=?";
        return findByHQL(hql, orderId);
    }
}
