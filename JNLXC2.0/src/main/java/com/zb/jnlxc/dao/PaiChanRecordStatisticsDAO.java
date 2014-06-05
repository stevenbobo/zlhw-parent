package com.zb.jnlxc.dao;

import com.ZLHW.base.dao.DAO;
import com.ZLHW.base.table.TableDeclare;
import com.zb.jnlxc.model.PaiChanRecord;
import com.zb.jnlxc.model.PaiChanRecordStatistics;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* Created by zhengbo.zb on 14-3-19.
*/
@Component
public class PaiChanRecordStatisticsDAO extends DAO<PaiChanRecordStatistics,Integer> {
    public void newPaiChanRecord(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        List<PaiChanRecordStatistics> l = this.findByHQL("from PaiChanRecordStatistics t where t.createDate = ?",date);
        if(l.size()==1){
            PaiChanRecordStatistics paiChanRecordStatistics = l.get(0);
            int num = paiChanRecordStatistics.getNextPaiChanRecordNum();
            paiChanRecordStatistics.setNextPaiChanRecordNum(num+1);
            this.update(paiChanRecordStatistics);
        }else {
            PaiChanRecordStatistics paiChanRecordStatistics = new PaiChanRecordStatistics();
            paiChanRecordStatistics.setCreateDate(date);
            paiChanRecordStatistics.setNextPaiChanRecordNum(2);
            this.create(paiChanRecordStatistics);
        }
    }

    public Integer getNextPaiChanRecordNum(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        List<PaiChanRecordStatistics> l = this.findByHQL("from PaiChanRecordStatistics t where t.createDate = ?",date);
        if(l.size()==1){
            PaiChanRecordStatistics paiChanRecordStatistics = l.get(0);
            return paiChanRecordStatistics.getNextPaiChanRecordNum();
        }else {
            PaiChanRecordStatistics paiChanRecordStatistics = new PaiChanRecordStatistics();
            paiChanRecordStatistics.setCreateDate(date);
            paiChanRecordStatistics.setNextPaiChanRecordNum(1);
            this.create(paiChanRecordStatistics);
            return 1;
        }
    }
}
