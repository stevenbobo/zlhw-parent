package com.zb.jnlxc.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Scheme;
import com.zb.jnlxc.service.SchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/SchemeAction")
@SessionAttributes(value = {"user"})
public class SchemeAction {
    Logger logger = LoggerFactory.getLogger(SchemeAction.class);
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Value("${JNLXC.temp_img_path}")
    private String  tempImgPath;
    @Value("${jnlxc.imagesRoot}")
    private String imagesRoot;
    @Value("${JNLXC.img_path}")
    private String imgPath ;
    @Value("${JNLXC.map_path}")
    private String mapPath ;
    @Resource
    SchemeService schemeService;
    @ResponseBody
    @RequestMapping("/addUpdateScheme")
    public Scheme addUpdateScheme(String schemeData, String uniqid,@ModelAttribute("user") Admin user,String operate)  {
        schemeData=schemeData.replace("\"payType\":\"\",","");
        logger.info("schemeData={}",schemeData);
        Scheme scheme = gson.fromJson(schemeData, Scheme.class);
        operate=operate==null?"addScheme":operate;
        if(operate.equals("addSchemeStartSchemeFlow"))
            return schemeService.addSchemeStartSchemeFlow(scheme, uniqid,user);
        else if(operate.equals("addSchemeWithOutFlow"))
            return schemeService.addSchemeWithOutFlow(scheme, uniqid, user);
        else if (operate.equals("editSchemeWithOutFlow"))
            return schemeService.editSchemeWithOutFlow(scheme, uniqid);
        else if (operate.equals("editSchemeStartSchemeFlow"))
            return schemeService.editSchemeStartSchemeFlow(scheme, uniqid);
        else if (operate.equals("editSchemeStartEditMouldFlow"))
            return schemeService.editSchemeStartEditMouldFlow(scheme, uniqid);
        else
            return null;
    }


    @ResponseBody
    @RequestMapping("/moulderLoock")
    public void moulderLoock(String taskId, Admin repairer,@ModelAttribute("user") Admin user)  {
        schemeService.moulderLoock(taskId, repairer,user);
    }

    @ResponseBody
    @RequestMapping("/repairerEditMould")
    public void repairerEditMould(String taskId,@ModelAttribute("user") Admin user) {
        schemeService.repairerEditMould(taskId,user);
    }

    /**
     * 查看图纸
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadScheme")
    public MiniPageRsp loadScheme(MiniPageReq page,HttpServletRequest request) {
        page.setRequest(request);
        schemeService.LoadScheme(page);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @RequestMapping(value = "/mapUpload")
    @ResponseBody
    public Map mapUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            return schemeService.changeToTempFile(file);
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/downTempImg")
    public void downTempImg(String uniqid,HttpServletResponse response) {
        download(response, tempImgPath + uniqid + ".bmp","image/jpeg");
    }

    @RequestMapping(value = "/downImg")
    public void downImg(Integer schemeId,HttpServletResponse response) {
        Scheme scheme = schemeService.getById(schemeId);
        download(response,imagesRoot+scheme.getBmpSrc(),"image/jpeg");
    }

    @RequestMapping(value = "/downDwg")
    public void downDwg(Integer schemeId,HttpServletResponse response) {
        Scheme scheme = schemeService.getById(schemeId);
        response.addHeader("Content-Disposition", "attachment;filename=" + scheme.getDbId()+".dwg");
        download(response,imagesRoot+scheme.getDwgSrc(),"application/octet-stream");
    }

    @ResponseBody
    @RequestMapping(value = "/getScheme")
    public Scheme getScheme(Integer schemeId){
        Scheme scheme =  schemeService.getById(schemeId);
        return scheme;
    }

    @ResponseBody
    @RequestMapping(value = "/getSchemeInfoByTask")
    public Scheme getSchemeInfoByTask(String taskId){
         return schemeService.getSchemeInfoByTask(taskId);
    }


    private void download(HttpServletResponse response,String filePath,String contentType) {
    	try{
	        response.setContentType(contentType);
	        OutputStream os = response.getOutputStream();
	        File file = new File(filePath);
	        if (!file.exists())
	            file = new File(imagesRoot+"default.bmp");
	        InputStream fis = new BufferedInputStream(new FileInputStream(file));
	        byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        fis.close();
	        os.write(buffer);
	        os.flush();
	        os.close();
    	}catch(IOException e){
    		logger.warn("找不到文件:{}",filePath);
    	}
    }


    @ResponseBody
    @RequestMapping("/deleteScheme")
    public void deleteScheme(Integer schemeId)  {
        schemeService.deleteScheme(schemeId);
    }

    @ResponseBody
    @RequestMapping("/getAllProductRecordDetail")
    public MiniPageRsp getAllProductRecordDetail(Integer schemeId){
        List l =schemeService.getAllProductRecordDetail(schemeId);
        return new MiniPageRsp(l,l.size());
    }

}
