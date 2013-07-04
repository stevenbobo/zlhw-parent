package com.zb.jnlxc.action;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.FlowStateImage;
import com.zb.jnlxc.model.HistoryTask;
import com.zb.jnlxc.model.MyTask;
import com.zb.jnlxc.service.FlowService;
import com.zb.jnlxc.service.TaskHistoryService;
import org.jbpm.api.*;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/FlowAction")
@SessionAttributes(value = {"user"})
public class FlowAction {
    @Resource
    FlowService flowService;
    @Resource
    TaskHistoryService taskHistoryService;

    @ResponseBody
    @RequestMapping("/getProcessInstanceByPage")
    public MiniPageRsp getProcessInstanceByPage(MiniPageReq page,HttpServletRequest request,@ModelAttribute("user") Admin user){
        page.setRequest(request);
         taskHistoryService.getProcessInstanceByPage(page,user);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @ResponseBody
    @RequestMapping("/getTaskHistoryDetail")
    public MiniPageRsp getTaskHistoryDetail(Long processInstanceId){
       List l = taskHistoryService.getTaskHistoryDetail(processInstanceId);
        return new MiniPageRsp(l);
    }

    @ResponseBody
    @RequestMapping("/loadTask")
    public MiniPageRsp loadTask(@ModelAttribute("user") Admin user, MiniPageReq page,HttpServletRequest request) throws ParseException {
        page.setRequest(request);
        flowService.loadTask(user, page);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }


    @ResponseBody
    @RequestMapping("/takeTask")
    public MyTask takeTask(String taskId,@ModelAttribute("user") Admin user) {
        return flowService.takeTask(taskId,user);
    }

    @ResponseBody
    @RequestMapping("/cancleTakeTask")
    public MyTask cancleTakeTask(String taskId) throws BaseErrorModel {
        return flowService.cancleTakeTask(taskId);    
    }

    @ResponseBody
    @RequestMapping("/findTaskOperaters")
    public List<ParticipationImpl> findTaskOperaters(String taskId) {
        return flowService.findTaskOperaters(taskId);    
    }

    @ResponseBody
    @RequestMapping("/getHistoryByExcutionId")
    public List<HistoryTask> getHistoryByExcutionId(String excutionId) {
        return flowService.getHistoryByExcutionId(excutionId);    
    }

    @ResponseBody
    @RequestMapping("/addOutTransition")
    public void addOutTransition(String taskIds, String destName, Map variables) {
        flowService.addOutTransition(taskIds, destName, variables);    
    }

    @ResponseBody
    @RequestMapping("/getFlowStateImage")
    public FlowStateImage getFlowStateImage(String taskId) {
        return flowService.getFlowStateImage(taskId);    
    }

    @ResponseBody
    @RequestMapping("/getFlowStateImageByExcutionId")
    public FlowStateImage getFlowStateImageByExcutionId(String excutionId) {
        return flowService.getFlowStateImageByExcutionId(excutionId);    
    }

    @ResponseBody
    @RequestMapping("/completeTask")
    public void completeTask(String taskId,@ModelAttribute("user") Admin user) {
        flowService.completeTask(taskId,user);
    }

    @ResponseBody
    @RequestMapping("/completeTask")
    public void completeTask(String taskId, Map map,@ModelAttribute("user") Admin user) throws BaseErrorModel {
        flowService.completeTask(taskId, map,user);
    }

    @ResponseBody
    @RequestMapping("/completeTask")
    public void completeTask(String taskId, String nextName,@ModelAttribute("user") Admin user) {
        flowService.completeTask(taskId, nextName,user);
    }

    @ResponseBody
    @RequestMapping("/completeTask")
    public void completeTask(String taskId, String nextName, Map map,@ModelAttribute("user") Admin user) {
        flowService.completeTask(taskId, nextName, map,user);
    }

    @ResponseBody
    @RequestMapping("/deployFlow")
    public String deployFlow(String path) {
        return flowService.deployFlow(path);    
    }

    @ResponseBody
    @RequestMapping("/deleteDeploymentCascade")
    public void deleteDeploymentCascade(String id) {
        flowService.deleteDeploymentCascade(id);    
    }

    @ResponseBody
    @RequestMapping("/startProcessInstanceByKey")
    public ProcessInstance startProcessInstanceByKey(String FlowName, String id) {
        return flowService.startProcessInstanceByKey(FlowName, id);    
    }

    @ResponseBody
    @RequestMapping("/deleteProcessInstanceCascade")
    public void deleteProcessInstanceCascade(String id) throws BaseErrorModel {
        flowService.deleteProcessInstanceCascade(id);    
    }

    @ResponseBody
    @RequestMapping("/startProcessInstanceByKey")
    public ProcessInstance startProcessInstanceByKey(String FlowName, String id, Map map) {
        return flowService.startProcessInstanceByKey(FlowName, id, map);    
    }

    @ResponseBody
    @RequestMapping("/findProcessInstanceByKey")
    public ProcessInstance findProcessInstanceByKey(String FlowName, String id) {
        return flowService.findProcessInstanceByKey(FlowName, id);    
    }

    @ResponseBody
    @RequestMapping("/getContentMap")
    public Object getContentMap(String taskId, String key) {
        return flowService.getContentMap(taskId, key);    
    }

    @ResponseBody
    @RequestMapping("/getContentMapByExecution")
    public Object getContentMapByExecution(String executionId, String key) {
        return flowService.getContentMapByExecution(executionId, key);    
    }

    @ResponseBody
    @RequestMapping("/testDeployFlowAll")
    public String testDeployFlowAll() {
        String path1 = "./jbpm/mouldFlow.jpdl.xml";
        String path2 = "./jbpm/orderFlow.jpdl.xml";
        String path3 = "./jbpm/paidan.jpdl.xml";
        String path4 = "./jbpm/productRecordFlow.jpdl.xml";
        String path5 = "./jbpm/schemeFlow.jpdl.xml";
        String result1 = flowService.deployFlow(path1);
        String result2 = flowService.deployFlow(path2);
        String result3 = flowService.deployFlow(path3);
        String result4 = flowService.deployFlow(path4);
        String result5 = flowService.deployFlow(path5);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/testDeleteFlow")
    public String testDeleteFlow(){
        List<Deployment> list = flowService.getRepositoryService().createDeploymentQuery().list();
        for(Deployment deployment:list){
            flowService.deleteDeploymentCascade(deployment.getId());
        }
        testDeployFlowAll();
        return "success";
    }
    @ResponseBody
    @RequestMapping("/testDeleteInstance")
    public String testDeleteInstance(){
        List<ProcessInstance> list1 = flowService.getExecutionService().createProcessInstanceQuery().list();
        for(ProcessInstance processInstance:list1){
			flowService.getExecutionService().deleteProcessInstanceCascade(processInstance.getId());
        }
        return "success";
    }
}
