package com.zing.demo001_shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zing.demo001_shiro.mapper")
public class Demo001ShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(Demo001ShiroApplication.class, args);
	}

}
