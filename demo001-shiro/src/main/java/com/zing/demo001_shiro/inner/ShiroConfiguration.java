package com.zing.demo001_shiro.inner;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zing create at 2020/2/17 4:52 下午
 * @version 0.0.1
 */
@Configuration
public class ShiroConfiguration {


    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("customizeRealm") CustomizeRealm customizeRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customizeRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager,
                                                         @Qualifier("tokenAccessFilter") TokenAccessFilter tokenAccessFilter,
                                                         @Qualifier("urlAccessFilter") UrlAccessControlFilter urlAccessControlFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> chains = new LinkedHashMap<>();
        chains.put("/auth/login", "anon");
        chains.put("/**", "tokenFilter,urlFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);

        Map<String, Filter> servletFilter = new LinkedHashMap<>();
        servletFilter.put("tokenFilter", tokenAccessFilter);
        servletFilter.put("urlFilter", urlAccessControlFilter);
        shiroFilterFactoryBean.setFilters(servletFilter);
        return shiroFilterFactoryBean;
    }


}
