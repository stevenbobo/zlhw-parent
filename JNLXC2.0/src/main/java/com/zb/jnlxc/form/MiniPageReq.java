package com.zb.jnlxc.form;

import com.ZLHW.base.Form.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-1-3
 * Time: 上午2:00
 * To change this template use File | Settings | File Templates.
 */
public class MiniPageReq extends Page {
    private HttpServletRequest request;
    private Integer pageIndex = 0;
    private String sortField;


    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
        super.setQueryCondition(request.getParameterMap());
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        super.setCurrentPage(pageIndex+1);
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        if(StringUtils.isNotBlank(sortField)){
            this.sortField = sortField;
            super.setSortKey(sortField);
        }
    }

    public String getParameter(String key){
        if(request==null)
            return null;
        else
            return request.getParameter(key);
    }

}
