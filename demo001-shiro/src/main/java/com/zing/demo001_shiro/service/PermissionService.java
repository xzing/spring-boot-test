package com.zing.demo001_shiro.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zing create at 2020/2/19 3:19 下午
 * @version 0.0.1
 */
@Slf4j
@Service
public class PermissionService {
    public boolean checkRoleUrlPermission(Integer roleId, String url) {
        log.info("role:{};url:{}", roleId, url);
        return "/auth/menu".equals(url);
    }
}
