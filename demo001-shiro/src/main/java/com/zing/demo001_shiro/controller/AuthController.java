package com.zing.demo001_shiro.controller;

import com.zing.demo001_shiro.bean.SystemUser;
import com.zing.demo001_shiro.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zing create at 2020/2/17 3:06 下午
 * @version 0.0.1
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody SystemUser systemUser) {
        if (systemUser == null || StringUtils.isBlank(systemUser.getAccount()) || StringUtils.isBlank(systemUser.getPassword())) {
            return "{\"code\":-1,\"msg\":\"用户名和密码不能为空\"}";
        }
        return loginService.login(systemUser);
    }

    @PostMapping("menu")
    public String myMenu(@RequestHeader("auth-token") String token) {
        return loginService.getMyMenu(token);
    }
}
