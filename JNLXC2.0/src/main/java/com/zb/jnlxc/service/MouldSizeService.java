package com.zb.jnlxc.service;

import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.MouldRecordDAO;
import com.zb.jnlxc.dao.MouldSizeDAO;
import com.zb.jnlxc.model.MouldRecord;
import com.zb.jnlxc.model.MouldSize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class MouldSizeService extends BaseService<MouldSizeDAO,MouldSize, Integer>{
    public Page loadList(Page page,String key){
        return this.findByPageWithHQL(page,"from MouldSize t where t.size like ?", "%"+key+"%");
    }

    public void update(MouldSize mouldSize){
        mouldSize.setCreatetime(new Date());
        super.update(mouldSize);
    }

}
