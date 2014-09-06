package com.zb.jnlxc.dao;

import java.util.List;

import com.ZLHW.base.dao.DAO;
import com.zb.jnlxc.model.LiaoKuang;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-16
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 */
@Component
public class LiaoKuangDAO extends DAO<LiaoKuang,Integer> {
    
    public LiaoKuang getBycode(String code){
        List<LiaoKuang> list = getByColumn("code", code);
        if(CollectionUtils.isEmpty(list)){
            return list.get(0);
        }else{
            return null;
        }
    }

}
