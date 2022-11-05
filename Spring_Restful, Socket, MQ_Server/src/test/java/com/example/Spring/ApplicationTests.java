package com.example.Spring;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Spring.controller.activeMQProducer.TopicProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private TopicProducer topicProducer;

    @Test
    void contextLoads() throws Exception {
        topicProducer.sendMsg("你好");
    }

//====================================================================================================

    @Test
    void getToken() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7); //token過期時間
        //Header默認值 HS256 JWT
        String token = JWT.create().withIssuedAt(new Date())//發行時間
                .withClaim("userId", 32)
                .sign(Algorithm.HMAC256("ASDFGHJ"));//密要
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjY5MjExMTcsInVzZXJJZCI6MzJ9.SlaHwNwj5PhFASfaTY2HPyoWwy2g_FktpjEwWuTjph8
        System.out.println(token);
    }

    @Test
    void verify() {    //驗證token
        //創建認證對象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("ASDFGHJ")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjY5MjExMTcsInVzZXJJZCI6MzJ9.SlaHwNwj5PhFASfaTY2HPyoWwy2g_FktpjEwWuTjph8");
        System.out.println(decodedJWT.getClaim("userId"));
    }
}
