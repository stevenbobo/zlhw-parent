package com.zb.jnlxc.form;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-1-3
 * Time: 上午2:00
 * To change this template use File | Settings | File Templates.
 */
public class MiniPageRsp  {
    private Object data;
    private int total=1000;

    public MiniPageRsp(List data) {
        this.data = data;
        this.total = data.size();
    }

    public MiniPageRsp(Object data, int total) {
        this.data = data;
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
