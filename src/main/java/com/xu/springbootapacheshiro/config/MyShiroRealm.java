package com.xu.springbootapacheshiro.config;

import com.xu.springbootapacheshiro.model.SysPermission;
import com.xu.springbootapacheshiro.model.SysRole;
import com.xu.springbootapacheshiro.model.UserInfo;
import com.xu.springbootapacheshiro.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserInfoService userInfoService;
    /*shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();当访问到页面的时候，
    链接配置了相应的权限或者shiro才会执行此方法，否则不会执行，所以如果这是简单的身份认证没有权限控制的
    话，那么这个方法可以不实现，直接返回null即可。在这个方法中主要使用类：SimpleAuthorizationInfo
    进行角色的添加和权限的添加
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置---》MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        UserInfo userInfo=(UserInfo)principals.getPrimaryPrincipal();
        for (SysRole role:userInfo.getRoleList()){
            authorizationInfo.addRole(role.getRole());
            for (SysPermission p:role.getPermissions()){
                 authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthorizationInfo()");
        //获取用户的输入的账号
        String username= (String) token.getPrincipal();
        System.out.println(token.getCredentials());
        //通过username从数据库中查找user对象吗，如果找到，没找到
        //实际项目中，这里可以根据实际情况做缓存，如果不做，shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo=userInfoService.findByUsername(username);
        System.out.println("---->>userInfo="+userInfo);
        if (userInfo==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(userInfo,userInfo.getPassword(),ByteSource.Util.bytes(userInfo.getCredentialsSalt()),getName());

        return authenticationInfo;
    }
}
