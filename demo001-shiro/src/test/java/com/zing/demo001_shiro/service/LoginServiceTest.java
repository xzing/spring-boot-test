package com.zing.demo001_shiro.service;

import com.zing.demo001_shiro.bean.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zing create at 2020/2/17 11:31 下午
 * @version 0.0.1
 */

@Slf4j
@SpringBootTest
public class LoginServiceTest {
    @Autowired
    LoginService loginService;

    @Test
    public void login() {
        SystemUser user = new SystemUser();
        user.setAccount("admin");
        user.setPassword("25d55ad283aa400af464c76d713c07ad");
        String data = loginService.login(user);
        log.info("data:{}", data);


    }
}
