package com.cq.wiki.util;

import com.cq.wiki.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static final Logger LOG = LoggerFactory.getLogger(TokenUtil.class);

    public static String signToken(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("name",user.getName());
        claims.put("loginName",user.getLoginName());

        long now = System.currentTimeMillis();
        LOG.info("当前时间的时间戳{}",now);
        //设置一天后过期
        long expirationTime = now + 24*3600;
        LOG.info("过期时间戳为：{}",expirationTime);

        return String.valueOf(Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(expirationTime))
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS256,"kejhfiwjkey87%&^GH*&UR%BJH")
                .compact()
        );
    }
    public static Boolean verifyToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey("kejhfiwjkey87%&^GH*&UR%BJH")
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(6281021777943L);
        user.setLoginName("cq");
        user.setName("程崎");
        System.out.println("token为："+signToken(user));

        System.out.println(verifyToken("eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbk5hbWUiOiJjcSIsIm5hbWUiOiLnqIvltI4iLCJpZCI6NjI4MTAyMTc3Nzk0MywiZXhwIjoxNjI0NTQzMDMxLCJpYXQiOjE2MjQ1NDI5NDV9.xEF9_OfCSVvYUkqh75XABwNYMjugk80TK-xnNDFHR5Q"));
    }

}
