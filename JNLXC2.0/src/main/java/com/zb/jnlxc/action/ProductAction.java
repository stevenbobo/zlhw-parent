package com.zb.jnlxc.action;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.form.ProductTrace;
import com.zb.jnlxc.model.*;
import com.zb.jnlxc.service.PaichanRecordService;
import net.sf.jasperreports.engine.JRException;
import org.dom4j.DocumentException;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/ProductAction")
@SessionAttributes(value = {"user"})
public class ProductAction {
    @Resource
    PaichanRecordService paichanRecordService;

    @ResponseBody
    @RequestMapping("/getProductTrace")
    public MiniPageRsp getProductTrace(String taskId){
        List result = paichanRecordService.getProductTrace(taskId);
        return new MiniPageRsp(result,result.size());
    }

    @RequestMapping("/downloadPDF")
    public void downloadPDF(HttpServletResponse response,String taskId) throws IOException {

        OutputStream out = response.getOutputStream();
        PaiChanRecord paiChanRecord = paichanRecordService.getPaiChanRecordByTaskId(taskId);
        response.setContentType("application/x-download");
        response.setHeader("Content-disposition", "attachment;filename="
                + paiChanRecord.getCode()+".pdf");
        try {
            paichanRecordService.startCreatePDF(taskId, out);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }


    @ResponseBody
    @RequestMapping("/loadProductRecord")
    public MiniPageRsp loadProductRecord(MiniPageReq page,HttpServletRequest request) throws ParseException {
        page.setRequest(request);
        paichanRecordService.loadProductRecordByPage(page);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @ResponseBody
    @RequestMapping("/getWorkDetail")
    public List<ProductRecordDetailHistory> getWorkDetail(int productId){
        return paichanRecordService.getWorkDetail(productId);
    }

    @ResponseBody
    @RequestMapping("/printForm")
    public void printForm(String taskId, String remarks,@ModelAttribute("user") Admin user) throws JRException, BaseErrorModel {
        paichanRecordService.printForm(taskId, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/startCreatePDF")
    public void startCreatePDF(String taskId, OutputStream os) throws JRException, DocumentException {
        paichanRecordService.startCreatePDF(taskId, os);
    }

    @ResponseBody
    @RequestMapping("/productionAudit")
    public void productionAudit(String taskId, String remarks,@ModelAttribute("user") Admin user){
        paichanRecordService.productionAudit(taskId, remarks, user);
    }


    @ResponseBody
    @RequestMapping("/shiXiao")
    public void shiXiao(String taskId,String productTraceDatail, String aginghardness, String alHeatNum, String remarks,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.shiXiao(taskId, traces, aginghardness, alHeatNum, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/yangHua")
    public void yangHua(String taskId,String productTraceDatail, String oxiFilm, String remarks,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.yangHua(taskId, traces, oxiFilm, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/dianYong")
    public void dianYong(String taskId,String productTraceDatail, String remarks,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.dianYong(taskId, traces, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/plating")
    public void plating(String taskId,String productTraceDatail, String remarks,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.plating(taskId, traces, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/pack")
    public void pack(String taskId,String productTraceDatail, String remarks,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.pack(taskId, traces, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/storage")
    public void storage(String taskId,String productTraceDatail, String storeLocation, String remarks,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.storage(taskId, traces, storeLocation, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/sendProduct")
    public void sendProduct(String taskId,String productTraceDatail, String finalWeight, String remarks,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.sendProduct(taskId, traces, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/financialReconciliation")
    public void financialReconciliation(String taskId, int totalPrice, String remarks,@ModelAttribute("user") Admin user){
        paichanRecordService.financialReconciliation(taskId, totalPrice, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/weight")
    public void weight(String taskId, String remarks,String productTraceDatail,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.weight(taskId, traces, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/transit")
    public void transit(String taskId, String remarks,String productTraceDatail,@ModelAttribute("user") Admin user){
        Gson gson = new Gson();
        List<ProductTrace> traces = gson.fromJson(productTraceDatail,new TypeToken<List<ProductTrace>>() {}.getType());
        paichanRecordService.transit(taskId, traces, remarks, user);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public void delete(PaiChanRecord paiChanRecord){
        paichanRecordService.delete(paiChanRecord);
    }

    @ResponseBody
    @RequestMapping("/deleteProductFlowInstanceCascade")
    public void deleteProductFlowInstanceCascade(String id){
        paichanRecordService.deleteProductFlowInstanceCascade(id);
    }

    @ResponseBody
    @RequestMapping("/deployFlow")
    public String deployFlow() {
        return paichanRecordService.deployFlow();
    }

    @ResponseBody
    @RequestMapping("/getProductRecordInfo")
    public Map getProductRecordInfo(String taskId) {
        return paichanRecordService.getProductRecordInfo(taskId);
    }

    @ResponseBody
    @RequestMapping("/startProductRecordFlowByKey")
    public ProcessInstance startProductRecordFlowByKey(String id) {
        return paichanRecordService.startProductRecordFlowByKey(id);
    }

    @ResponseBody
    @RequestMapping("/startProductRecordFlowByKey")
    public ProcessInstance startProductRecordFlowByKey(String id, Map map) {
        return paichanRecordService.startProductRecordFlowByKey(id, map);
    }

    @ResponseBody
    @RequestMapping("/findProductRecordFlowInstanceByKey")
    public ProcessInstance findProductRecordFlowInstanceByKey(String id) {
        return paichanRecordService.findProductRecordFlowInstanceByKey(id);
    }
}
