package com.zb.jnlxc.JBPM;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.dao.PaiChanRecordDAO;
import com.zb.jnlxc.model.PaiChanRecord;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

/**
 * Created by zhengbo.zb on 14-5-2.
 */
public class ProcessDecisionHandler implements DecisionHandler {
    @Override
    public String decide(OpenExecution openExecution) {
        Integer paichanRecordId=(Integer) openExecution.getVariable("paichanRecordId");
        PaiChanRecordDAO dao=(PaiChanRecordDAO)BeanFactory.LookUp("paiChanRecordDAO");
        PaiChanRecord paiChanRecord=dao.loadById(paichanRecordId);
        return paiChanRecord.getNextStep();
    }
}
