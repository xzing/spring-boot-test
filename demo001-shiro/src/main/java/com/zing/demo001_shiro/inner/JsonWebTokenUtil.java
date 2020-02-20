package com.zing.demo001_shiro.inner;

import com.alibaba.fastjson.JSON;
import com.zing.demo001_shiro.bean.SystemUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author zing create at 2020/2/17 11:09 下午
 * @version 0.0.1
 */
@Component
public class JsonWebTokenUtil {
    public static final int TTL = 24 * 3600000;

    public static final String KEY = "ZCSMART";


    public String createToken(SystemUser user, Map<String, Object> data) {
        if (user == null || user.getUserId() == null || StringUtils.isBlank(user.getAccount())) {
            throw new UnsupportedOperationException("用户信息有误，无法创建Token！" + user);
        }
        JwtBuilder builder = Jwts.builder()
                .setId(user.getUserId().toString())
                .setSubject(user.getAccount())
                .signWith(SignatureAlgorithm.HS256, KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TTL))
                .addClaims(data)
                .claim("user", JSON.toJSONString(user));
        return builder.compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }

    public SystemUser getUserByToken(String token) {
        return JSON.parseObject(((String) parseToken(token).get("user")), SystemUser.class);
    }

}
