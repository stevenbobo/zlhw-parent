package com.zb.jnlxc.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Mould;
import com.zb.jnlxc.model.MouldRecord;
import com.zb.jnlxc.service.PaiDanFlowService;
import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/PaiDanFlowAction")
@SessionAttributes(value = {"user"})
public class PaiDanFlowAction{
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    Logger logger = LoggerFactory.getLogger(PaiDanFlowAction.class);
    @Resource
    PaiDanFlowService paiDanFlowService;
    @ResponseBody
    @RequestMapping("/startmouldFlowByKey")
    public ProcessInstance startmouldFlowByKey(String id, Map map) {
        return paiDanFlowService.startmouldFlowByKey(id, map);
    }

    @ResponseBody
    @RequestMapping("/startPaiDanFlow")
    public void startPaiDanFlow(Integer mouldId) {
        paiDanFlowService.startPaiDanFlow(mouldId);
    }

    @ResponseBody
    @RequestMapping("/distribution")
    public void distribution(String taskId, Integer moulderId,String remarks,@ModelAttribute("user") Admin user) {
        paiDanFlowService.distribution(taskId, moulderId,remarks, user);
    }

    @ResponseBody
    @RequestMapping("/record")
    public void record(String taskId, String recordData, String nextStep,String remarks,@ModelAttribute("user") Admin user) {
        logger.info("recordData={}",recordData);
        MouldRecord record = gson.fromJson(recordData, MouldRecord.class);
        paiDanFlowService.record(taskId, record, nextStep,remarks,user);
    }

    @ResponseBody
    @RequestMapping("/danhua")
    public void danhua(String taskId,String remarks,@ModelAttribute("user") Admin user) {
        paiDanFlowService.danhua(taskId,remarks,user);
    }

    @ResponseBody
    @RequestMapping("/panding")
    public void panding(String taskId, String nextStep,String remarks,String scrapId,@ModelAttribute("user") Admin user) {
        paiDanFlowService.panding(taskId,nextStep,remarks,scrapId,user);
    }

    @ResponseBody
    @RequestMapping("/storage")
    public void storage(String taskId,String remarks,@ModelAttribute("user") Admin user) {
        paiDanFlowService.storage(taskId,remarks,user);
    }
}
