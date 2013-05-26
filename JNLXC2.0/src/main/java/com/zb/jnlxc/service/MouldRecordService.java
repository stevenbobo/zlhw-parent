package com.zb.jnlxc.service;

import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.MouldRecordDAO;
import com.zb.jnlxc.model.MouldRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MouldRecordService extends BaseService<MouldRecordDAO,MouldRecord, Integer>{
    public List<MouldRecord> getMouldRecordByMould(Integer mouldId){
        return this.findHql("from MouldRecord t where t.mould.dbId=?",mouldId);
    }
}
