package com.zb.jnlxc.action;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.form.TreeNode;
import com.zb.jnlxc.model.*;
import com.zb.jnlxc.service.AdminService;
import com.zb.jnlxc.service.AdminUserGroupService;
import com.zb.jnlxc.service.UserGroupService;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Controller
@SessionAttributes(value = {"user"})
@RequestMapping("/AdminAction")
public class AdminAction {
    public static Map<String, String> userMap = new ConcurrentHashMap<String, String>();
    Logger logger = LoggerFactory.getLogger(AdminAction.class);

    @Resource
    private AdminService adminService;
    @Resource
    private UserGroupService userGroupService;
    @Resource
    private AdminUserGroupService adminUserGroupService;

    @ResponseBody
    @RequestMapping("/editUserGroup")
    public void editUserGroup(String data)  {
        List<UserGroup> lists = new Gson().fromJson(data,new TypeToken<List<UserGroup>>(){}.getType());
        for(UserGroup ug:lists){
            if(ug.getDbId()==null){
                userGroupService.create(ug);
            }else {
                userGroupService.update(ug);
            }

        }
    }



    @ResponseBody
    @RequestMapping("/removeUserGroup")
    public void removeUserGroup(Integer userGroupId)  {
        userGroupService.deleteById(userGroupId);
    }

    @ResponseBody
    @RequestMapping("/editPassword")
    public boolean editPassword(Admin admin, String oldPass, String newPass)  {
        return adminService.editPassword(admin, oldPass, newPass);    
    }

    @ResponseBody
    @RequestMapping("/editPasswordById")
    public boolean editPasswordById(Integer dbid, String newPass)  {
        return adminService.editPasswordById(dbid, newPass);    
    }

    @ResponseBody
    @RequestMapping("/resetPassword")
    public boolean resetPassword(Integer dbid)  {
        return adminService.resetPassword(dbid);    
    }


    @ResponseBody
    @RequestMapping("/getViewTablesByAdmin")
    public List<TreeNode> getViewTablesByAdmin(@ModelAttribute("user")Admin user) {
        List<AuthorDetail> l = adminService.getViewTablesByAdmin(user);
        return transForDetails(l);
    }

    @ResponseBody
    @RequestMapping("/getAuthorDetails")
    public List<TreeNode> getAuthorDetails() {
        List<AuthorDetail> l = adminService.getAuthorDetails();
        return transForDetails(l);
    }

    @ResponseBody
    @RequestMapping("/getAuthorDetailsByGroup")
    public List<TreeNode> getAuthorDetailsByGroup(UserGroup userGroup) {
        List<AuthorDetail> l = adminService.getAuthorDetailsByGroup(userGroup);
        return transForDetails(l);
    }

    private List<TreeNode> transForDetails(List<AuthorDetail> l){
        List<TreeNode> resultList = new ArrayList<TreeNode>();
        for(AuthorDetail detail:l){
            TreeNode node = new TreeNode();
            node.setId(detail.getDbId()+"");
            node.setText(detail.getName());
            if(detail.getParentId()==null){
                node.setLeaf(false);
            } else {
                node.setContent(detail.getPath());
                node.setParentid(detail.getParentId()+"");
                node.setLeaf(true);
            }
            resultList.add(node);
        }
        return resultList;
    }


    @ResponseBody
    @RequestMapping("/findAllUserGroup")
    public List<UserGroup> findAllUserGroup(@ModelAttribute("user") Admin user) {
        return adminService.findAllUserGroup( user);
    }


    @RequestMapping("/login")
    @ResponseBody
    //用户登录验证
    public Object login(Admin admin,HttpSession session) {
        logger.info("当前尝试登陆的用户:{}",admin);
        Admin user =adminService.adminExist(admin);

        if(user==null){
            return new BaseErrorModel("无此用户","baseview").toMap();
        }else{
            if(user.getState()==(byte)2){
                return new BaseErrorModel("已离职用户，无法登陆","baseview").toMap();
            }
            if(user.getPassword().equals(admin.getPassword())) {
                session.setAttribute("user", user);
                userMap.put(user.getAccount(), session.getId());
                logger.info("登陆成功:{}",admin);
            }else{
                return new BaseErrorModel("密码错误","baseview").toMap();
            }
        }
        return userMap;
    }

    @ResponseBody
    @RequestMapping("/changePassword")
    public void changePassword(@ModelAttribute("user") Admin user,String oldPassword,String newPassword,String rePassword){
        Admin admin = adminService.getById(user.getDbId());
        if(!admin.getPassword().equals(oldPassword))
             throw new BaseErrorModel("您输入的原始密码错误","baseview");
        if(StringUtils.isEmpty(newPassword)||!newPassword.equals(rePassword))
            throw new BaseErrorModel("两次输入的新密码不一致","baseview");

        adminService.changePassword(user.getDbId(),newPassword);
        user.setPassword(newPassword);
    }

    @ResponseBody
    @RequestMapping("/getCurrentUser")
    public Admin getCurrentUser(@ModelAttribute("user") Admin user) {
        return user;
    }

    @RequestMapping("/loginOut")
    public String loginOut(@ModelAttribute("user") Admin user,HttpSession session,SessionStatus sessionStatus) {
        logger.info(user.getAccount()+"退出");
        session.removeAttribute("user");
        sessionStatus.setComplete();
        return "redirect:/login.vm";
    }

    @ResponseBody
    @RequestMapping("/findAdmins")
    public MiniPageRsp findAdmins(MiniPageReq page,@ModelAttribute("user") Admin user) {
        adminService.findAdmins(page,user);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }



    @ResponseBody
    @RequestMapping("/editAdmin")
    public void editAdmin(String data,String userGroups) {
        List<Admin> lists = new Gson().fromJson(data,new TypeToken<List<Admin>>(){}.getType());
        for (Admin admin:lists){
            List<Integer> ugs = new ArrayList<Integer>();
            if (admin.getDbId()==null)
                adminService.create(admin);
            else
                adminService.update(admin);
        }
    }

    @ResponseBody
    @RequestMapping("/deleteAdmin")
    public void deleteAdmin(Integer dbId) {
        adminUserGroupService.deleteForAdmin(dbId);
            adminService.deleteById(dbId);
    }


    @ResponseBody
    @RequestMapping("/getPowerAdmins")
    public List<Admin> getPowerAdmins(String powerName) {
        return adminService.getPowerAdmins(powerName);    
    }

    @ResponseBody
    @RequestMapping("/getPowerAdmins")
    public List<Admin> getPowerAdmins(Integer id) {
        return adminService.getPowerAdmins(id);    
    }


    @ResponseBody
    @RequestMapping("/editAuthorDetails")
    public void  editAuthorDetails(UserGroup userGrup,String details){
        Set<Integer> ds = new HashSet<Integer>(1000);
        if(StringUtils.isEmpty(details)){
            ds.add(0);
        }else{
            String[] temp = details.split(",");
            for (int a=0;a<temp.length;a++){
                ds.add(Integer.parseInt(temp[a]));
            }
        }
        adminService.editAuthorDetails( userGrup,ds);
    }


    @ResponseBody
    @RequestMapping("/findAllAgent")
    public List findAllAgent() {
        return adminService.findAllAgent();
    }

    @ResponseBody
    @RequestMapping("/findAll")
    public List findAll() {
        return adminService.findAll();
    }

    @ResponseBody
    @RequestMapping("/findByName")
    public List<Admin> findByName(String key) {
        return adminService.findByName(key);
    }

    @ResponseBody
    @RequestMapping("/findAllRepairer")
    public List findAllRepairer() {
        return adminService.findAllRepairer();    
    }

    @ResponseBody
    @RequestMapping("/findAllWaixie")
    public List findAllWaixie() {
        return adminService.findAllWaixie();    
    }
    
	/**
	 * 查找所有的机台用户
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/findAllJiTai")
	public List findAllJiTai(){
		return adminService.findAllJiTai();
	}
}
