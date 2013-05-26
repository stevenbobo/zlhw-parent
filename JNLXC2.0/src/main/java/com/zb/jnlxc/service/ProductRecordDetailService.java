package com.zb.jnlxc.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.ProductRecordDetailDAO;
import com.zb.jnlxc.model.ProductRecord;
import com.zb.jnlxc.model.ProductRecordDetail;
@Transactional
@Service
public class ProductRecordDetailService extends BaseService<ProductRecordDetailDAO,ProductRecordDetail, Integer> {
	private static final Log log = LogFactory.getLog(ProductRecordDetailService.class);
	public List<ProductRecordDetail>  getProductRecordDetailByProductRecord(ProductRecord productRecord){
		
		return this.getDao().getByColumn("productRecord", productRecord);
	}
}
