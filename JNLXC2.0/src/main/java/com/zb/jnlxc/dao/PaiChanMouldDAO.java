package com.zb.jnlxc.dao;

import java.util.List;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.PaiChanMould;
import com.zb.jnlxc.model.PaiChanRecord;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: zhengbo.zb
 * Date: 14-2-4
 * Time: 下午7:25
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PaiChanMouldDAO extends DAO<PaiChanMould,Integer> {
    public PaiChanMould getUnJiYaPaiChanMould(Integer mouldId){
        List<PaiChanMould> list = findByHQL("from PaiChanMould t where t,mould.dbId =? and hasJiYa=?", mouldId,true);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }else{
            return null;
        }
    }
}
