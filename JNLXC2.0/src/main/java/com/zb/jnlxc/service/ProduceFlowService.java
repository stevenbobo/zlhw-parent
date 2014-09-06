package com.zb.jnlxc.service;

import javax.annotation.Resource;

import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.zb.jnlxc.dao.*;
import com.zb.jnlxc.model.*;
import com.zb.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private ProductDetailShiXiaoDAO productDetailShiXiaoDAO;
    @Resource
    private ProductDetailDAO productDetailDAO;
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
     * 获取生产任务
     * @return
     */
    public Page loadLiaoKuang(Page page){
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from LiaoKuang t where 1=1 ");
        if(StringUtils.isNotEmpty(page.getParameter("currentStep"))){
            byte currentStep=Byte.parseByte(page.getParameter("currentStep"));
            hql.append("and t.currentStep=:RefCurrentStep ");
            queryConditions.add(new QueryCondition("RefCurrentStep", currentStep));
        }
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        liaoKuangDAO.findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

    /**
     * 料框挤压过磅
     */
    public void liaoKuangWeight(Integer liaoKuangId,Integer weight,Admin admin){
        LiaoKuang liaoKuang = liaoKuangDAO.getById(liaoKuangId);
        LiaoKuangWeightRecord liaoKuangWeightRecord = new LiaoKuangWeightRecord();
        liaoKuangWeightRecord.setLiaoKuang(liaoKuang);
        liaoKuangWeightRecord.setWeight(weight);
        liaoKuangWeightRecord.setCreateDate(new Date());
        liaoKuangWeightRecord.setOperator(admin);
        liaoKuangWeightRecordDAO.create(liaoKuangWeightRecord);
        String nextStep = liaoKuang.getNextStep();
        liaoKuang.setCurrentStep(nextStep);
        liaoKuangDAO.update(liaoKuang);
    }
    /**
     * 产品时效
     */
    private void shixiao(Integer liaoKuangId,Integer productDetailId,ProductDetailShiXiao productDetailShiXiao,Admin admin){
        ProductDetail productDetail = productDetailDAO.getById(productDetailId);
        productDetailShiXiao.setOperator(admin);
        productDetailShiXiao.setCreateDate(new Date());
        productDetailShiXiao.setProductDetail(productDetail);
        productDetailShiXiaoDAO.create(productDetailShiXiao);

    }


    /**
     * 完成时效工序
     */
    private void shixiaoFinish(Integer liaoKuangId,Integer weight,Admin admin){
        LiaoKuang liaoKuang = liaoKuangDAO.getById(liaoKuangId);
        String nextStep = liaoKuang.getNextStep();
        liaoKuang.setCurrentStep(nextStep);
        liaoKuangDAO.update(liaoKuang);
    }

    /**
     * 氧化上排
     * @param productDetailId
     * @param liaoKuangId
     */
    private void yanghuaShangpai(Integer productDetailId,Integer liaoKuangId){
        ProductDetail productDetail = productDetailDAO.getById(productDetailId);

    }

    /**
     * 氧化上排
     * @param productDetailId
     * @param liaoKuangId
     */
    private void yanghuaXiaji(Integer productDetailId,Integer liaoKuangId){
    }

    /**
     * 打包
     */
    private void pack(){

    }

}
