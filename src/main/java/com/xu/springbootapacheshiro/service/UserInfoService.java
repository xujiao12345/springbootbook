package com.xu.springbootapacheshiro.service;

import com.xu.springbootapacheshiro.model.UserInfo;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
}
