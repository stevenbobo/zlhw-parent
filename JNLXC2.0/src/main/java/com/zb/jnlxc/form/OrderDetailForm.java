package com.zb.jnlxc.form;

import com.zb.jnlxc.model.OrderDetail;
import com.zb.jnlxc.model.PaichanOrderDetail;
import org.springframework.beans.BeanUtils;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-1-20
 * Time: 下午7:29
 * To change this template use File | Settings | File Templates.
 */
public class OrderDetailForm extends OrderDetail {
   private String _state;

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }

    public OrderDetail getOrderDetail(){
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(this,orderDetail);
        if(orderDetail.getCompStatus()==null)
            orderDetail.setCompStatus((byte)0);
        return orderDetail;
    }

    public PaichanOrderDetail getPaichanOrderDetail(){
        PaichanOrderDetail paichanOrderDetail = new PaichanOrderDetail();
        BeanUtils.copyProperties(this,paichanOrderDetail);
        if(paichanOrderDetail.getCompStatus()==null)
            paichanOrderDetail.setCompStatus((byte)0);
        return paichanOrderDetail;
    }
}
