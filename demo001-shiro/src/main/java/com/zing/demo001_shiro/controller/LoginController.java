package com.zing.demo001_shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create at     2020/1/31 4:10 下午
 *
 * @author zing
 * @version 0.0.1
 */
@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/add")
    public String add() {
        return "shiro/add";
    }

    @RequestMapping("/update")
    public String update() {
        return "shiro/update";
    }
}
