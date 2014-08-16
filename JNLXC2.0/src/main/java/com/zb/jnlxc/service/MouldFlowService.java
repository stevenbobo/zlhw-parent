package com.zb.jnlxc.service;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.MouldDAO;
import com.zb.jnlxc.dao.PaiChanMouldDAO;
import com.zb.jnlxc.dao.SchemeDAO;
import com.zb.jnlxc.model.Mould;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    DataDictService dataDictService;
}
