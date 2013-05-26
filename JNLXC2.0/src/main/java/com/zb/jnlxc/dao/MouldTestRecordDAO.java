package com.zb.jnlxc.dao;

import com.zb.jnlxc.model.Mould;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.MouldTestRecord;
@Component
public class MouldTestRecordDAO extends DAO<MouldTestRecord,Integer>{
    public void deleteByMould(Mould mould){
        this.updateHql("delete from MouldTestRecord m where m.mould =?",mould);
    }
}
