package com.zb.jnlxc.service;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.MouldTestRecordDAO;
import com.zb.jnlxc.model.MouldRecord;
import com.zb.jnlxc.model.MouldTestRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MouldTestRecordService extends BaseService<MouldTestRecordDAO,MouldTestRecord, Integer>{
    public List<MouldTestRecord> getTestRecordByMould(Integer mouldId){
        return this.findHql("from MouldTestRecord t where t.mould.dbId=?",mouldId);
    }
}
