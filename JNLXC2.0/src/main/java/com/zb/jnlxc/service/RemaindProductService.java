package com.zb.jnlxc.service;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.DistributionRecordDAO;
import com.zb.jnlxc.dao.OrderDetailDAO;
import com.zb.jnlxc.dao.ProductTeamDAO;
import com.zb.jnlxc.dao.RemaindProductDAO;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.model.*;
import com.zb.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhengbo.zb on 2014/6/3.
 */
@Transactional
@Service
public class RemaindProductService  extends BaseService<RemaindProductDAO,RemaindProduct, Integer> {
    public static Logger logger = LoggerFactory.getLogger(RemaindProductService.class);

    @Resource
    private OrderDetailDAO orderDetailDAO;
    @Resource
    private DistributionRecordDAO distributionRecordDAO;


    public void distributionToOrderDetail(Integer remaindProductId,Integer orderDetailId,Integer quantity,Admin user){
        RemaindProduct remaindProduct = getById(remaindProductId);
        OrderDetail orderDetail = orderDetailDAO.getById(orderDetailId);
        if(quantity>remaindProduct.getRemaindQuantity()){
            throw new BaseErrorModel("超出库存数量","");
        }
        int unCompQuantity = orderDetail.getOrderQuantity()-orderDetail.getCompQuantity();
        if(quantity>unCompQuantity){
            throw new BaseErrorModel("超出未完成的订购数量","");
        }

        DistributionRecord distributionRecord = new DistributionRecord();
        distributionRecord.setRemaindProduct(remaindProduct);
        distributionRecord.setOrderDetail(orderDetail);
        distributionRecord.setQuantity(quantity);
        distributionRecord.setCreateDate(new Date());
        distributionRecord.setOperator(user);
        Integer setSize = Integer.parseInt(orderDetail.getSetSize());
        float kpm = orderDetail.getOrderForm().getKpm();
        Integer weight =(int)(quantity*((setSize/1000.0)*kpm)/1000);
        distributionRecord.setWeight(weight);
        distributionRecordDAO.create(distributionRecord);
        orderDetail.setCompQuantity(orderDetail.getCompQuantity() + quantity);
        orderDetail.setCompWeight(orderDetail.getCompWeight() + weight);
        orderDetailDAO.update(orderDetail);
        remaindProduct.setRemaindQuantity(remaindProduct.getRemaindQuantity()-quantity);
        update(remaindProduct);
    }

    public Page loadRemaindProduct(MiniPageReq page, Admin user) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from RemaindProduct t where 1=1 ");
//        hql.append("and t.remaindQuantity>0 ");
        if(StringUtils.isNotEmpty(page.getParameter("startDate"))){
            hql.append("and t.createDate >=:startDate ");
            Date startDate=sdf.parse(page.getParameter("startDate"));
            queryConditions.add(new QueryCondition("startDate", startDate));
        }
        if(StringUtils.isNotEmpty(page.getParameter("endDate"))){
            hql.append("and t.createDate <=:endDate ");
            Date endDate=sdf.parse(page.getParameter("endDate"));
            queryConditions.add(new QueryCondition("endDate", endDate));
        }
        if(StringUtils.isNotEmpty(page.getParameter("schemeId"))){
            int schemeId=Integer.parseInt(page.getParameter("schemeId"));
            hql.append("and t.paichanOrderDetail.orderDetail.orderForm.scheme.dbId=:RefSchemeId ");
            queryConditions.add(new QueryCondition("RefSchemeId", schemeId));
        }
        hql.append(" order by createDate desc");
        this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

    public Page loadDistributionRecord(MiniPageReq page, Admin user)  throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from DistributionRecord t where 1=1 ");
        if(StringUtils.isNotEmpty(page.getParameter("startDate"))){
            hql.append("and t.createDate >=:startDate ");
            Date startDate=sdf.parse(page.getParameter("startDate"));
            queryConditions.add(new QueryCondition("startDate", startDate));
        }
        if(StringUtils.isNotEmpty(page.getParameter("endDate"))){
            hql.append("and t.createDate <=:endDate ");
            Date endDate=sdf.parse(page.getParameter("endDate"));
            queryConditions.add(new QueryCondition("endDate", endDate));
        }
        if(StringUtils.isNotEmpty(page.getParameter("code"))){
            String code =  page.getParameter("code");
            hql.append("and t.orderDetail.orderForm.code like :code  ");
            queryConditions.add(new QueryCondition("code", "%"+code+"%"));
        }
        hql.append(" order by createDate desc");
        this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }
}
