package com.zb.jnlxc.JBPM;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.dao.ProductRecordDAO;
import com.zb.jnlxc.model.ProductRecord;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

/**
 * Created by zhengbo.zb on 14-5-2.
 */
public class ProcessDecisionHandler implements DecisionHandler {
    @Override
    public String decide(OpenExecution openExecution) {
        Integer productRecordId=(Integer) openExecution.getVariable("productRecordId");
        ProductRecordDAO dao=(ProductRecordDAO) BeanFactory.LookUp("productRecordDAO");
        ProductRecord productRecord=dao.getById(productRecordId);
        return productRecord.getNextStep();
    }
}
