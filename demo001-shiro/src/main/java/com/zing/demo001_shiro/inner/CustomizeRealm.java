package com.zing.demo001_shiro.inner;

import com.zing.demo001_shiro.bean.SystemUser;
import com.zing.demo001_shiro.mapper.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zing create at 2020/2/17 4:56 下午
 * @version 0.0.1
 */
@Slf4j
@Component
public class CustomizeRealm extends AuthorizingRealm {

    public void setName() {
        this.setName("customizeRealm");
    }

    //
    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //授权用户号角色
        SystemUser user = (SystemUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(user.getRoleKey());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("认证");
        // 获取用户名和密码
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        SystemUser user = systemUserMapper.selectByAccount(upt.getUsername());
        if (user != null && user.getPassword().equals(new String(upt.getPassword()))) {
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        } else {
            // 无权限
            throw new RuntimeException("登录失败，用户名或密码不正确");
        }
    }
}
