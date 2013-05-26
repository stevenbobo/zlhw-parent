package com.zb.jnlxc.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.*;
import com.zb.jnlxc.service.MouldRecordService;
import com.zb.jnlxc.service.MouldService;
import com.zb.jnlxc.service.MouldTestRecordService;
import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/MouldAction")
@SessionAttributes(value = {"user"})
public class MouldAction {
    Logger logger = LoggerFactory.getLogger(MouldAction.class);
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Resource
    private MouldService mouldService;
    @Resource
    private MouldRecordService mouldRecordService;
    @Resource
    private MouldTestRecordService mouldTestRecordService;
    @ResponseBody
    @RequestMapping("/generateCode")
    public String generateCode(Integer schemeId) {
        return mouldService.generateCode(schemeId);
    }

    @ResponseBody
    @RequestMapping("/saveUpdateMould")
    public Mould saveMould(String mouldData,String operate)  {
        logger.info("mouldData={}",mouldData);
        Mould mould = gson.fromJson(mouldData, Mould.class);
        operate=operate==null?"saveMould":operate;
        if(operate.equals("saveMould")){
            return mouldService.saveMould(mould);
        }else if (operate.equals("updateMould")){
            return mouldService.updateMould(mould);
        }else {
            return null;
        }

    }

    @ResponseBody
    @RequestMapping("/saveMouldWidthFlow")
    public Mould saveMouldWidthFlow(String mouldData,String taskId,@ModelAttribute("user") Admin user)  {
        logger.info("mouldData={}",mouldData);
        Mould mould = gson.fromJson(mouldData, Mould.class);
        return mouldService.saveMould(mould,taskId,user);

    }
    @ResponseBody
    @RequestMapping("/getUsedMouldListByScheme")
    public MiniPageRsp getUsedMouldListByScheme(Integer schemeId){
        return new MiniPageRsp(mouldService.getUsedMouldListByScheme(schemeId)) ;
    }

    @ResponseBody
    @RequestMapping("/getMouldRecords")
    public MiniPageRsp getMouldRecords(Integer mouldId){
        return new MiniPageRsp(mouldRecordService.getMouldRecordByMould(mouldId));

    }

    @ResponseBody
    @RequestMapping("/getMouldTestRecords")
    public MiniPageRsp getMouldTestRecords(Integer mouldId){
        return new MiniPageRsp(mouldTestRecordService.getTestRecordByMould(mouldId));

    }

        @ResponseBody
    @RequestMapping("/saveMouldByTaskId")
    public Mould saveMouldByTaskId(Mould mould, String taskId,@ModelAttribute("user") Admin user)  {
        return mouldService.saveMould(mould, taskId,user);
    }

    @ResponseBody
    @RequestMapping("/loadMould")
    public MiniPageRsp loadMould(MiniPageReq page,HttpServletRequest request) {
        page.setRequest(request);
        mouldService.LoadMould(page);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @ResponseBody
    @RequestMapping("/getMould")
    public Mould getMould(Integer mouldId)  {
        return mouldService.getById(mouldId);
    }

    @ResponseBody
    @RequestMapping("/deleteMould")
    public void deleteMould(Integer mouldId)  {
        mouldService.deleteMould(mouldId);
    }

    @ResponseBody
    @RequestMapping("/updateMould")
    public void updateMould(Mould mould)  {
        mouldService.updateMould(mould);    
    }

    @ResponseBody
    @RequestMapping("/scrapMould")
    public void scrapMould(Mould mould,@ModelAttribute("user") Admin user)  {
        mouldService.scrapMould(mould,user);
    }

    @ResponseBody
    @RequestMapping("/getMouldInfo")
    public Mould getMouldInfo(String taskId) {
        return mouldService.getMouldInfo(taskId);    
    }

    @ResponseBody
    @RequestMapping("/buyMouldComplete")
    public void buyMouldComplete(String taskId, String remark,@ModelAttribute("user") Admin user)  {
        mouldService.buyMouldComplete(taskId, remark,user);
    }

    @ResponseBody
    @RequestMapping("/changeToRepairMould")
    public void changeToRepairMould(String taskId, Admin repair, String remark,@ModelAttribute("user") Admin user)  {
        mouldService.changeToRepairMould(taskId, repair, remark,user);
    }

    @ResponseBody
    @RequestMapping("/changeToNormalMould")
    public void changeToNormalMould(String taskId, String remark,@ModelAttribute("user") Admin user)  {
        mouldService.changeToNormalMould(taskId, remark,user);
    }

    @ResponseBody
    @RequestMapping("/changeToReturn")
    public void changeToReturn(String taskId, String remark,@ModelAttribute("user") Admin user)  {
        mouldService.changeToReturn(taskId, remark,user);
    }

    @ResponseBody
    @RequestMapping("/changeToScrapped")
    public void changeToScrapped(String taskId, Integer scrapId,@ModelAttribute("user") Admin user)  {
        mouldService.changeToScrapped(taskId, scrapId,user);
    }

    @ResponseBody
    @RequestMapping("/moulderLook")
    public void moulderLook(String taskId, boolean success,@ModelAttribute("user") Admin user) {
        mouldService.moulderLook(taskId, success,user);
    }

    @ResponseBody
    @RequestMapping("/findAllNormalMouldsByScheme")
    public List<Mould> findAllNormalMouldsByScheme(Integer schemeId) {
        return mouldService.findAllNormalMouldsByScheme(schemeId);
    }

    @ResponseBody
    @RequestMapping("/deployFlow")
    public String deployFlow() {
        return mouldService.deployFlow();    
    }

    @ResponseBody
    @RequestMapping("/startmouldFlowByKey")
    public ProcessInstance startmouldFlowByKey(String id) {
        return mouldService.startmouldFlowByKey(id);    
    }

    @ResponseBody
    @RequestMapping("/startmouldFlowByKey")
    public ProcessInstance startmouldFlowByKey(String id, Map map) {
        return mouldService.startmouldFlowByKey(id, map);    
    }

    @ResponseBody
    @RequestMapping("/findProcessInstanceByKey")
    public ProcessInstance findProcessInstanceByKey(String id) {
        return mouldService.findProcessInstanceByKey(id);    
    }

    @ResponseBody
    @RequestMapping("/delete")
    public void delete(Mould mould)  {
        mouldService.delete(mould);    
    }

    @ResponseBody
    @RequestMapping("/waixieBuy")
    public void waixieBuy(String taskId ,String remarks,@ModelAttribute("user") Admin user) {
        mouldService.waixieBuy(taskId,remarks,user);
    }

    @ResponseBody
    @RequestMapping("/shoumu")
    public void shoumu(String taskId, String remarks,@ModelAttribute("user") Admin user) {
        mouldService.shoumu(taskId, remarks,user);
    }

    @ResponseBody
    @RequestMapping("/testModelRecord")
    public void testModelRecord(String taskId,String mouldTestRecordData ,String remarks,@ModelAttribute("user") Admin user) {
        logger.info("mouldTestRecordData = {}",mouldTestRecordData);
        MouldTestRecord mouldTestRecord = gson.fromJson(mouldTestRecordData,MouldTestRecord.class);
        mouldService.testModelRecord(taskId,  mouldTestRecord,remarks,user);
    }

    @ResponseBody
    @RequestMapping("/deside")
    public void deside(String taskId, String remarks, String nextStep,@ModelAttribute("user") Admin user,HttpServletRequest request) {
        String scrapId = request.getParameter("scrapId");
        mouldService.deside(taskId, remarks, nextStep,scrapId,user);
    }
}
