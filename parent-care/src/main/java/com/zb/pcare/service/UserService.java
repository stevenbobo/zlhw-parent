package com.zb.pcare.service;

import com.zb.pcare.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-4
 * Time: 下午9:52
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserService {
    public void createUser(User user){
        user.setCreateDate(new Date());
    }
}
