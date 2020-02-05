package com.zing.demo001_shiro.inner;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create at     2020/1/31 5:01 下午
 * shiro 主配置类
 *
 * @author zing
 * @version 0.0.1
 */
@Configuration
public class ShiroConfiguration {
    /**
     * 认证授权器
     */
    @Bean
    public CustomizeRealm customizeRealm() {
        return new CustomizeRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("customizeRealm") CustomizeRealm customizeRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customizeRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // shiroFilterFactoryBean.setLoginUrl("login");
        return shiroFilterFactoryBean;
    }
}
