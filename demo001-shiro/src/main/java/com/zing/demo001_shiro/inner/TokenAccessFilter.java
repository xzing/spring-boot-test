package com.zing.demo001_shiro.inner;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author zing create at 2020/2/19 2:06 下午
 * @version 0.0.1
 */

@Slf4j
@Component
public class TokenAccessFilter extends AccessControlFilter {
    @Autowired
    JsonWebTokenUtil jwtUtil;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("auth-token");
        log.info("token:{}", token);
        try {
            return StringUtils.isNotBlank(token) && Objects.nonNull(jwtUtil.getUserByToken(token));
        } catch (Exception e) {
            log.warn("e", e);
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("filter onAccessDenied!");
        WebUtils.toHttp(servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "10000");
        return false;
    }
}
