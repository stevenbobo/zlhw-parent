package com.zb.jnlxc.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zb.jnlxc.dao.LiaoKuangDAO;

@Transactional
@Service
public class ProduceFlowService {
    Logger logger = LoggerFactory.getLogger(ProduceFlowService.class);
    @Resource
    private LiaoKuangDAO liaoKuangDAO;
    
}
