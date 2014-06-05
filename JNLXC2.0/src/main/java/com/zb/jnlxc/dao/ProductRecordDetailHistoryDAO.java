package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class ProductRecordDetailHistoryDAO extends DAO<ProductRecordDetailHistory,Integer> {
	private static final Log log = LogFactory.getLog(ProductRecordDetailHistoryDAO.class);

    /**
     * 记录生产记录日志
     * @param paichanOrderDetail
     * @param taskName
     * @param admin
     */
    public void createByProductDetail(PaichanOrderDetail paichanOrderDetail,String taskName,Admin admin) {
        ProductRecordDetailHistory productRecordDetailHistory = new ProductRecordDetailHistory();
        productRecordDetailHistory.setOperater_dbId(admin.getDbId());
        productRecordDetailHistory.setOperater_Name(admin.getName());
        productRecordDetailHistory.setPaichanRecord_dbId(paichanOrderDetail.getPaichanRecord().getDbId());
        productRecordDetailHistory.setOrderDetail_dbId(paichanOrderDetail.getOrderDetail().getDbId());
        productRecordDetailHistory.setDetailQuantity(paichanOrderDetail.getCompQuantity());
        productRecordDetailHistory.setDetailWeight(paichanOrderDetail.getCompWeight());
        productRecordDetailHistory.setTaskName(taskName);
        productRecordDetailHistory.setOperateDate(new Date());
        productRecordDetailHistory.setSetSize(paichanOrderDetail.getOrderDetail().getSetSize());
        productRecordDetailHistory.setWcomment(paichanOrderDetail.getPaichanRecord().getWcomment());
        this.create(productRecordDetailHistory);
    }
}
