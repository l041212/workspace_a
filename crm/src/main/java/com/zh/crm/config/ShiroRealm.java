package com.zh.crm.config;


import com.zh.crm.entity.User;
import com.zh.crm.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ShiroRealm  extends AuthorizingRealm {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object key = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = new User();
        try {
            BeanUtils.copyProperties(user, key);
        } catch (Exception e) {
        }
        Set<String> roles = userService.findRolesByUserId(user.getUserId());
        authorizationInfo.addRoles(roles);
        Set<String> perms = userService.findPersByUserId(user.getUserId());
        authorizationInfo.addStringPermissions(perms);
        logger.info("---- 获取到以下权限 ----");
        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 11----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String number = token.getUsername();
        String password = String.valueOf(token.getPassword());
        System.out.println(token.getHost());
        Integer extNumber = 0 ;
        if(token.getHost()==null||token.getHost().equals("")){

        }else{
            extNumber = Integer.valueOf(token.getHost());
        }

        // 从数据库获取对应用户名密码的用户
        User user = userService.findUserByName(number,password);
        user.setExtNumber(extNumber);
        System.out.println(extNumber);
        if (user != null) {
            // 用户为禁用状态
            if (1==user.getStatus()) {
                throw new DisabledAccountException();
            }
            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user, //用户
                    user.getPassword(), //密码
                    getName()  //realm name
            );
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user",user);
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    }
}
