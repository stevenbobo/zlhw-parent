package com.zb.jnlxc.service;

import javax.annotation.Resource;

import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.LiaoKuang;
import com.zb.jnlxc.model.LiaoKuangWeightRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
public class ProduceFlowService {
    Logger logger = LoggerFactory.getLogger(ProduceFlowService.class);
    @Resource
    private LiaoKuangDAO liaoKuangDAO;
    private static final Log log = LogFactory.getLog(PaichanRecordService.class);
    @Resource
    private OrderFormDAO orderFormDAO;
    @Resource
    private LiaoKuangWeightRecordDAO liaoKuangWeightRecordDAO;
    @Resource
    private ReportService reportService;
    @Resource
    private OrderDetailDAO orderDetailDAO;
    @Resource
    private PaichanOrderDetailDAO paichanOrderDetailDAO;
    @Resource
    private ProductRecordDetailHistoryDAO productRecordDetailHistoryDAO;
    @Resource
    private ProductTeamService productTeamService;
    @Value("${print.server.url}")
    private String printServerUrl;
    @Value("${print.server.port}")
    private int printServerPort;
    @Resource
    FlowService flowService;
    @Resource
    AdminDAO adminDAO;

    /**
     * ÁÏ¿ò¹ý°õ
     */
    public void liaoKuangWeight(Integer liaoKuangId,Integer weight,Admin admin){
        LiaoKuang liaoKuang = liaoKuangDAO.getById(liaoKuangId);
        LiaoKuangWeightRecord liaoKuangWeightRecord = new LiaoKuangWeightRecord();
        liaoKuangWeightRecord.setLiaoKuang(liaoKuang);
        liaoKuangWeightRecord.setWeight(weight);
        liaoKuangWeightRecord.setCreateDate(new Date());
        liaoKuangWeightRecord.setOperator(admin);
        liaoKuang.setJiya_weight(weight);
        liaoKuangWeightRecordDAO.create(liaoKuangWeightRecord);
    }
    
}
