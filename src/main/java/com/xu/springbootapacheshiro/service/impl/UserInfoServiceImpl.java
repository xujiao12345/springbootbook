package com.xu.springbootapacheshiro.service.impl;

import com.xu.springbootapacheshiro.dao.UserInfoDao;
import com.xu.springbootapacheshiro.model.UserInfo;
import com.xu.springbootapacheshiro.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");

        return userInfoDao.findByUsername(username);
    }
}
