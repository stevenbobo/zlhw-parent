package com.zb.jnlxc.service;

import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.MouldDAO;
import com.zb.jnlxc.dao.MouldFixTaskDAO;
import com.zb.jnlxc.dao.PaiChanMouldDAO;
import com.zb.jnlxc.dao.SchemeDAO;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.PaiChanRecord;
import com.zb.jnlxc.model.Scheme;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-16
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class MouldFlowService extends BaseService<MouldDAO,Mould, Integer> {
    private static final Logger log = LoggerFactory.getLogger(MouldFlowService.class);

    @Resource
    private SchemeDAO schemeDao;
    @Resource
    private FlowService flowService;
    @Resource
    private PaiChanMouldDAO paiChanMouldDAO;
    @Resource
    private MouldFixTaskDAO mouldFixTaskDAO;
    @Resource
    DataDictService dataDictService;

    /**
     * 获取转修任务
     * @return
     */
    public Page loadMouldFixTask(Page page){
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from MouldFixTask t where 1=1 and isProcessed = false ");
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        mouldFixTaskDAO.findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

    /**
     * 获取拋拍任务
     * @return
     */
    public Page loadMouldPaoPaiTask(Page page){
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from MouldPaoPaiTask t where 1=1 and isProcessed = false ");
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        mouldFixTaskDAO.findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }

    /**
     * 获取拋拍任务
     * @return
     */
    public Page loadMouldDanHuaTask(Page page){
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from MouldDanHuaTask t where 1=1 and isProcessed = false ");
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        mouldFixTaskDAO.findByPageWithTmpHQL(page, hql.toString(), queryConditions);
        return page;
    }
}
