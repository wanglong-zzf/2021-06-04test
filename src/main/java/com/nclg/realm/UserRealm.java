package com.nclg.realm;

import com.nclg.bean.User;
import com.nclg.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
  
               @Override
       public String getName() {
                  return "customRealm";
              }
  
               /**
         * 认证
         */
               @Override
       protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                   // 获取用户名称
                   String username = (String) token.getPrincipal();
                   User user = userService.findByUsername(username);
                   if (user == null) {
                           // 用户名不存在抛出异常
                           System.out.println("认证：当前登录的用户不存在");
                           throw new UnknownAccountException();
                       }
                   String pwd = user.getPassword();
                   return new SimpleAuthenticationInfo(user, pwd, getName());
               }
  
               /**
         * 授权
         */
               @Override
       protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection princ) {
                   return null;
               }
}
