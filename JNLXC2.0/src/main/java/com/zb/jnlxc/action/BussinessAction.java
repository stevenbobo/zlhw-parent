package com.zb.jnlxc.action;

import com.ZLHW.base.Form.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.form.OrderDetailForm;
import com.zb.jnlxc.model.*;
import com.zb.jnlxc.service.BusinessService;
import org.jbpm.api.ProcessInstance;
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
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/BussinessAction")
@SessionAttributes(value = {"user"})
public class BussinessAction {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Resource
    BusinessService businessService;


    @ResponseBody
    @RequestMapping("/getOrderFormsByScheme")
    public MiniPageRsp getOrderFormsByScheme(Integer schemeId) {
        return new MiniPageRsp( businessService.getOrderFormsByScheme(schemeId));
    }

    @ResponseBody
    @RequestMapping("/getProductRecordsByScheme")
    public MiniPageRsp getProductRecordsByScheme(Integer schemeId) {
        return new MiniPageRsp(businessService.getProductRecordsByScheme(schemeId));
    }

    @ResponseBody
    @RequestMapping("/loadOrderForm")
    public MiniPageRsp loadOrderForm(MiniPageReq page,@ModelAttribute("user") Admin user,HttpServletRequest request) throws ParseException {
        page.setRequest(request);
         businessService.loadOrderForm(page,user);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @ResponseBody
    @RequestMapping("/getLastOrderForm")
    public OrderForm getLastOrderForm(Integer schemeId,Integer clientId) throws ParseException {
        return businessService.getLastOrderForm(schemeId,clientId);
    }



    @ResponseBody
    @RequestMapping("/getOrderDetail")
    public MiniPageRsp getOrderDetail(Integer orderId){
        List data =  businessService.getOrderDetail(orderId);
        return new MiniPageRsp(data,data.size());
    }

    @ResponseBody
    @RequestMapping("/getOrderDetailByScheme")
    public MiniPageRsp getOrderDetailByScheme(Integer SchemeId){
        List data =  businessService.getOrderDetailByScheme(SchemeId);
        return new MiniPageRsp(data);
    }

    @ResponseBody
    @RequestMapping("/getOrder")
    public OrderForm getOrder(Integer orderId){
        return businessService.getById(orderId);
    }

    @ResponseBody
    @RequestMapping("/getOrderFormsByAgent")
    public Page getOrderFormsByAgent(Page page) {
        return businessService.getOrderFormsByAgent(page);    
    }

    @ResponseBody
    @RequestMapping("/getCompleteOrderForms")
    public Page getCompleteOrderForms(Page page) {
        return businessService.getCompleteOrderForms(page);    
    }


    @ResponseBody
    @RequestMapping("/loadAgents")
    public List loadAgents() {
        return businessService.loadAgents();    
    }

    @ResponseBody
    @RequestMapping("/getAllAgents")
    public List getAllAgents() {
        return businessService.getAllAgents();    
    }

    @ResponseBody
    @RequestMapping("/getProductRecordsByOrderForm")
    public List getProductRecordsByOrderForm(OrderForm orderForm) {
        return businessService.getProductRecordsByOrderForm(orderForm);    
    }

    @ResponseBody
    @RequestMapping("/getOrderDetailsByProductRecord")
    public List getOrderDetailsByProductRecord(ProductRecord productRecord) {
        return businessService.getOrderDetailsByProductRecord(productRecord);    
    }


    @ResponseBody
    @RequestMapping("/saveAuthorizedAgents")
    public void saveAuthorizedAgents(List<Admin> authorizedAgents,@ModelAttribute("user") Admin user) {
        businessService.saveAuthorizedAgents(authorizedAgents,user);
    }

    @ResponseBody
    @RequestMapping("/initOrderProxy")
    public Map initOrderProxy() {
        return businessService.initOrderProxy();    
    }

    @ResponseBody
    @RequestMapping("/generateCode")
    public String generateCode(Integer clientId) {
        return businessService.generateCode(clientId);
    }
    @ResponseBody
    @RequestMapping("/saveUpdateOrder")
    public OrderForm saveUpdateOrder(String orderFormData,String orderDetailData,String operate,String taskId,@ModelAttribute("user") Admin user){
        OrderForm orderForm = gson.fromJson(orderFormData,OrderForm.class);
        List<OrderDetailForm> orderDetails = gson.fromJson(orderDetailData,new TypeToken<List<OrderDetailForm>>(){}.getType());
        operate=operate==null?"saveOrder":operate;
        if(operate.equals("saveOrder")){
            return businessService.saveOrder(orderForm,orderDetails,user);
//        }else if (operate.equals("updateOrder")){
//            return businessService.updateOrder(orderForm,orderDetails);
        }else if (operate.equals("completeUpdateOrder")){
            return businessService.completeUpdateOrder(taskId, orderForm, orderDetails, user);
        }else {
            return orderForm;
        }
    }


    @ResponseBody
    @RequestMapping("/getOrderFormAuthor")
    public List<Admin> getOrderFormAuthor(Admin loginAgent) {
        return businessService.getOrderFormAuthor(loginAgent);    
    }

    @ResponseBody
    @RequestMapping("/deleteProcessInstanceCascade")
    public void deleteProcessInstanceCascade(String executionId) {
        businessService.deleteProcessInstanceCascade(executionId);    
    }

    @ResponseBody
    @RequestMapping("/productionAudit")
    public void productionAudit(String taskId, boolean success, String remarks,@ModelAttribute("user") Admin user) {
        businessService.productionAudit(taskId, success, remarks,user);
    }

    @ResponseBody
    @RequestMapping("/agentAudit")
    public void agentAudit(String taskId, boolean success,String remarks,@ModelAttribute("user") Admin user) {
        businessService.agentAudit(taskId, success,remarks,user);
    }

    @ResponseBody
    @RequestMapping("/moulderAudit")
    public void moulderAudit(String taskId, boolean success, String remarks,@ModelAttribute("user") Admin user) {
        businessService.moulderAudit(taskId, success, remarks,user);
    }

    @ResponseBody
    @RequestMapping("/getOrderFormInfo")
    public OrderForm getOrderFormInfo(String taskId) {
        return businessService.getOrderFormInfo(taskId);    
    }

    @ResponseBody
    @RequestMapping("/deployFlow")
    public String deployFlow() {
        return businessService.deployFlow();    
    }

    @ResponseBody
    @RequestMapping("/ProcessInstance")
    public ProcessInstance startOrderFlowByKey(String id) {
        return businessService.startOrderFlowByKey(id);    
    }

    @ResponseBody
    @RequestMapping("/ProcessInstance")
    public ProcessInstance startOrderFlowByKey(String id, Map map) {
        return businessService.startOrderFlowByKey(id, map);    
    }

    @ResponseBody
    @RequestMapping("/ProcessInstance")
    public ProcessInstance findProcessInstanceByKey(String id) {
        return businessService.findProcessInstanceByKey(id);    
    }

    @ResponseBody
    @RequestMapping("/deleteOrder")
    public void deleteOrder(Integer orderId) {
        businessService.deleteOrder(orderId);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public void delete(OrderForm orderForm) {
        businessService.delete(orderForm);    
    }
}
