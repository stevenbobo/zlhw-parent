package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ProductRecordDetailHistoryDAO extends DAO<ProductRecordDetailHistory,Integer> {
	private static final Log log = LogFactory.getLog(ProductRecordDetailHistoryDAO.class);

    /**
     * 记录生产记录日志
     * @param productRecordDetail
     * @param taskName
     * @param admin
     */
    public void createByProductDetail(ProductRecordDetail productRecordDetail,String taskName,Admin admin) {
        ProductRecordDetailHistory productRecordDetailHistory = new ProductRecordDetailHistory();
        productRecordDetailHistory.setOperater_dbId(admin.getDbId());
        productRecordDetailHistory.setOperater_Name(admin.getName());
        productRecordDetailHistory.setProductRecord_dbId(productRecordDetail.getProductRecord().getDbId());
        productRecordDetailHistory.setOrderDetail_dbId(productRecordDetail.getOrderDetail().getDbId());
        productRecordDetailHistory.setDetailQuantity(productRecordDetail.getDetailQuantity());
        productRecordDetailHistory.setDetailWeight(productRecordDetail.getDetailWeight());
        productRecordDetailHistory.setTaskName(taskName);
        productRecordDetailHistory.setOperateDate(new Date());
        productRecordDetailHistory.setSetSize(productRecordDetail.getOrderDetail().getSetSize());
        productRecordDetailHistory.setWcomment(productRecordDetail.getProductRecord().getWcomment());
        this.create(productRecordDetailHistory);
    }
}
