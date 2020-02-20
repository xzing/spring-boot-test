package com.zing.demo001_shiro.inner;

import com.zing.demo001_shiro.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zing create at 2020/2/18 4:37 下午
 * @version 0.0.1
 */
@Slf4j
@Component("urlAccessFilter")
public class UrlAccessControlFilter extends AccessControlFilter {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JsonWebTokenUtil jwtUtil;

    // public UrlAccessControlFilter(PermissionService permissionService) {
    //     this.permissionService = permissionService;
    // }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("auth-token");
        Integer roleId = jwtUtil.getUserByToken(token).getRoleId();
        String url = ((HttpServletRequest) servletRequest).getRequestURI();
        return permissionService.checkRoleUrlPermission(roleId, url);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.warn("访问未授权URL");
        WebUtils.toHttp(servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "10000");
        return false;
    }
}
