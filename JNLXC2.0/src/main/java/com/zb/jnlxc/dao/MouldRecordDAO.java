package com.zb.jnlxc.dao;

import com.zb.jnlxc.model.Mould;
import org.springframework.stereotype.Component;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.MouldRecord;
@Component
public class MouldRecordDAO extends DAO<MouldRecord,Integer>{
    public void deleteByMould(Mould mould){
        this.updateHql("delete from MouldRecord m where m.mould =?",mould);
    }
}
