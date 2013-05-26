package com.zb.jnlxc.service;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.SchemeDAO;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Scheme;
import com.zb.util.DwgToBitmap;
import com.zb.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class SchemeService extends BaseService<SchemeDAO,Scheme, Integer>{

    @Value("${JNLXC.temp_map_path}")
    private String  tempMapPath;

    @Value("${JNLXC.temp_img_path}")
    private String  tempImgPath;

    @Value("${JNLXC.map_path}")
    private String  mapPath;

    @Value("${JNLXC.img_path}")
    private String  imgPath;

    @Value("${jnlxc.imagesRoot}")
    private String imagesRoot;

	private static final Log log = LogFactory.getLog(SchemeService.class);
	@Resource
	private FlowService flowService;
	
	/**
	 * 修改临时文件
	 * param: 传入dwg文件名
	 * return: 临时bmp文件路径
	 */
	@SuppressWarnings("unchecked")
	public Map changeToTempFile(MultipartFile file) throws IOException {
        Map<String,String> map = new HashMap<String, String>();
        String tempName = System.currentTimeMillis()+"";
        File tempMap = new File(tempMapPath+tempName+".dwg");
        file.transferTo(tempMap);
		String newBmpFile=tempImgPath+tempName+".bmp";
		DwgToBitmap img=new DwgToBitmap(tempMap.getPath(),newBmpFile);
        img.convertToBitmap();
        map.put("uniqid",tempName);
        return map;
	}

    public void changeOldMap(File file){
        try{
        if(file.isFile()&&file.getName().indexOf("dwg")!=-1){
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String name = file.getName().replace(".dwg","");
            Integer schemeId = Integer.parseInt(name);
            Scheme scheme = getById(schemeId);
            logger.info("schemeId={},scheme={}",schemeId,scheme);
            if(scheme!=null){

                //新dwg文件名
                String dwgFileName = format.format(scheme.getDesignDate())+"_"+scheme.getDbId()+".dwg";
                //新bmp文件名
                String bmpFileName = format.format(scheme.getDesignDate())+"_"+scheme.getDbId()+".bmp";
                //新dwg文件路径
                String dwgFileNamePath = mapPath+dwgFileName;
                //新bmp文件路径
                String bmpFileNamePath = imgPath+bmpFileName;
                DwgToBitmap img=new DwgToBitmap(file.getPath(),bmpFileNamePath);
                img.convertToBitmap();
                copyFile(file.getPath(),dwgFileNamePath);
                logger.info("操作成功:schemeId={}",schemeId);
            }
        }
        }catch (Exception e){
            logger.error("出错",e);
        }
    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ( (byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

	//新增图纸, 并开启流程
	public Scheme addSchemeStartSchemeFlow(Scheme scheme,String uniqid,Admin user) throws BaseErrorModel{
        addSchemeWithOutFlow(scheme,uniqid,user);
        Map map = new HashMap();
		map.put("schemeId", scheme.getDbId());
		//开启新建图纸流程
		startSchemeFlowByKey(scheme.getCode() ,map);
		return scheme;
	}

    /**
     * 用于前台获取的图纸流程信息
     * @param taskId
     * @return
     */
    public Scheme getSchemeInfoByTask(String taskId){
        Map map=new HashMap();
        Integer schemeId=(Integer)flowService.getContentMap(taskId, "schemeId");
        return this.getById(schemeId);
    }
	
	//新增图纸, 不开启流程
	public Scheme addSchemeWithOutFlow(Scheme scheme,String uniqid,Admin user) throws BaseErrorModel{
        List schemeList  = this.getDao().findByHQL(" from Scheme s where s.code = ? ", scheme.getCode());
        if(schemeList!=null && schemeList.size()>0)
            throw new BaseErrorModel("已经存在相同编号的图纸","");
        this.create(scheme);

        changeToFormalPaht(uniqid,scheme);

        //保存图纸名称
        scheme.setCode(String.format("%1$06d", scheme.getDbId()));
        scheme.setDesigner(user);
        scheme.setCurrentState((byte) 1);
        scheme.setNextMouldNum(1);
        this.update(scheme);
        return scheme;
	}

    private void changeToFormalPaht(String uniqid,Scheme scheme){
        //临时dwg文件
        File tempDwgFile = new File(tempMapPath+uniqid+".dwg");
        //临时bmp文件
        File tempBmpFile = new File(tempImgPath+uniqid+".bmp");
        if(tempDwgFile.exists()&&tempBmpFile.exists()){
            Date sqlDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            //新dwg文件名
            String dwgFileName = format.format(sqlDate)+"_"+scheme.getDbId()+".dwg";
            //新bmp文件名
            String bmpFileName = format.format(sqlDate)+"_"+scheme.getDbId()+".bmp";

            //新dwg文件路径
            String dwgFileNamePath = mapPath+dwgFileName;
            //转移临时文件
            tempDwgFile.renameTo(new File(dwgFileNamePath));
            //新bmp文件路径
            String bmpFileNamePath = imgPath+bmpFileName;
            //转移临时文件
            tempBmpFile.renameTo(new File(bmpFileNamePath));
            scheme.setBmpSrc("img/" + bmpFileName);
            scheme.setDwgSrc("map/" + dwgFileName);
            scheme.setDesignDate(sqlDate);
        }
    }

    public void update(Scheme scheme){
        scheme.setCode(String.format("%1$06d", scheme.getDbId()));
        super.update(scheme);
    }
	
	//修改图纸
	@SuppressWarnings("unchecked")
	public Scheme editSchemeWithOutFlow(Scheme scheme,String uniqid) throws BaseErrorModel{
		if(StringUtils.isNotEmpty(uniqid)){
            changeToFormalPaht(uniqid, scheme);
		}
        update(scheme);
        return scheme;
    }
	
	//修改图纸,开始模具流程
	@SuppressWarnings("unchecked")
	public Scheme editSchemeStartSchemeFlow(Scheme scheme,String uniqid) throws BaseErrorModel{

        if(StringUtils.isNotEmpty(uniqid)){
            changeToFormalPaht(uniqid,scheme);
        }
        update(scheme);
        Map map = new HashMap();
        map.put("schemeId", scheme.getDbId());
        //开启新建图纸流程
        startSchemeFlowByKey(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + "_" +scheme.getCode() ,map);
        return scheme;

	}
	
	//修改图纸,开始修模流程
	@SuppressWarnings("unchecked")
	public Scheme editSchemeStartEditMouldFlow(Scheme scheme,String uniqid) throws BaseErrorModel{
        if(StringUtils.isNotEmpty(uniqid)){
            changeToFormalPaht(uniqid,scheme);
        }
        update(scheme);
        Map map = new HashMap();
		map.put("schemeId", scheme.getDbId());
		flowService.startProcessInstanceByKey("EditMouldFlow","Edit_"+scheme.getCode(),map);
        return scheme;
	}
	/**
	 * 主管查看指定修模工
	 * @param taskId
	 * @param repairer
	 * @throws BaseErrorModel
	 */
	public void moulderLoock(String taskId , Admin repairer,Admin user) throws BaseErrorModel{
		Map map = new HashMap();
		map.put("repairer", repairer.getName());
		flowService.completeTask(taskId,map,user);
	}
	/**
	 * 修模工修改模具
	 * @param taskId
	 */
	public void repairerEditMould(String taskId,Admin user){
		flowService.completeTask(taskId,user);
	}



	/**
	 * 通过传入的参数page分页显示图纸
	 */
	@SuppressWarnings("unchecked")
	public Page LoadScheme(Page page)
	{
        List<QueryCondition> queryConditions=new ArrayList();
        StringBuffer hql=new StringBuffer("from Scheme s where 1=1 ");
        if(StringUtils.isNotEmpty(page.getParameter("productType"))){
            Integer productTypeId = Integer.parseInt(page.getParameter("productType"));
            hql.append("and s.productType.dbId=:productType ");
            queryConditions.add(new QueryCondition("productType", productTypeId));
        }
        if(StringUtils.isNotEmpty(page.getParameter("name"))){
            String name = page.getParameter("name");
            hql.append("and ( s.code like :name or s.name like :name )");
            queryConditions.add(new QueryCondition("name", "%"+name+"%"));
        }
        hql.append("order by ").append(page.getSortKey()).append(" ").append(page.getSortOrder());
        this.getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
		return page;
	}
	
	
	/**
	 * 删除图纸
	 * @throws BaseErrorModel 
	 */
	public void deleteScheme(Integer schemeId) throws BaseErrorModel
	{
        Scheme scheme =  this.loadById(schemeId);
        flowService.deleteProcessInstanceCascade("schemeFlow."+scheme.getCode());
        this.delete(scheme);
		FileUtil.deleteFile(imgPath+scheme.getBmpSrc());
		FileUtil.deleteFile(imgPath+scheme.getDwgSrc());
		
	}
	/**
	 * 发布一个订单流程图(流程版本，只发布一次，平常不用)
	 *  流程图jpdl位置（比如"./com/zb/JBPM/orderFlow.jpdl.xml"）
	 * @return
	 */
	public String deployFlow(){
		String path="./com/zb/JBPM/scheme.jpdl.xml";
		return flowService.getRepositoryService().createDeployment().addResourceFromClasspath(path).deploy();
	}
	/**
	 * 开启图纸订单流程
	 * @param id 模具订单号
	 * @return
	 */
	public ProcessInstance startSchemeFlowByKey(String id){
		return flowService.startProcessInstanceByKey("schemeFlow",id);
	}
	/**
	 * 开启图纸订单流程
	 * @param id 模具订单号
	 * @return
	 */
	public ProcessInstance startSchemeFlowByKey(String id,Map map){
		return flowService.startProcessInstanceByKey("schemeFlow",id,map);
	}
	

	/**
	 * 找到当前的流程实例
	 * @param id
	 * @return
	 */
	public ProcessInstance findProcessInstanceByKey(String id){
		return flowService.findProcessInstanceByKey("schemeFlow", id);
	}
	/**
	 * 获取流程变量
	 * @param taskId
	 * @return
	 */
	public Map getSchemeFlowValues(String taskId){
		Map map = new HashMap();
		Integer schemeId=(Integer)flowService.getContentMap(taskId, "schemeId");
		map.put("scheme", this.getById(schemeId));
		map.put("payWay", flowService.getContentMap(taskId, "payWay"));
		map.put("mouldFee", flowService.getContentMap(taskId, "mouldFee"));
		map.put("feeCharge", flowService.getContentMap(taskId, "feeCharge"));
		return map;
	}
	
	public List getAllProductRecordDetail(Integer schemeId){
		String HQL1="select new map( t.setSize as setSize,sum(t.compQuantity) as compQuantity,sum(t.compWeight) as compWeight) " +
                "from OrderDetail t where t.orderForm.scheme.dbId=?  group by t.setSize";
		String HQL2="select new map( t.setSize as setSize,sum(t.deliveryQuantity) as deliveryQuantity,sum(t.deliveryWeight) as deliveryWeight) " +
                "from Delivery t where t.scheme.dbId=? group by t.setSize";
		List<Map> l1=this.findHql(HQL1, schemeId);
		List<Map> l2=this.findHql(HQL2, schemeId);
        Map<String,Map> map = new HashMap();
		for(Map os:l1){
			map.put((String)os.get("setSize"), os);
		}
		for(Map ss:l2){
			String setSize=(String)ss.get("setSize");
			Map os=map.get(setSize);
			Long compQuantity=(Long)os.get("compQuantity");
            Long comWeight=(Long)os.get("compWeight");
            Long deliveryQuantity=(Long)ss.get("deliveryQuantity");
            Long deliveryWeight=(Long)ss.get("deliveryWeight");
            os.put("compQuantity",compQuantity-deliveryQuantity);
            os.put("compWeight",comWeight-deliveryWeight);
		}
		return l1;
	}
	
}
