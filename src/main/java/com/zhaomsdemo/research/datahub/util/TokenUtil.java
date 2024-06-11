package com.zhaomsdemo.research.datahub.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.zhaomsdemo.research.datahub.constant.CommonConstant.TIME_STAMP;
import static com.zhaomsdemo.research.datahub.constant.CommonConstant.USER_ID;
import static com.zhaomsdemo.research.datahub.constant.CommonConstant.USER_ROLE;

@Component
public class TokenUtil {

    @Value("${jwt.token.secretKey}")
    private String secretKey;

    public String generateToken(String userId) {
        String token = JWT.create()
                .withClaim(USER_ID,userId)
                .withClaim(USER_ROLE, "ROLE_ADMIN")
                .withClaim(TIME_STAMP,System.currentTimeMillis())
                .sign(Algorithm.HMAC256(secretKey));
        return token;
    }

    public Map<String, String> parseToken(String token) {
        Map<String, String> map = new HashMap<>();
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);
        Claim userId = decodedJWT.getClaim(USER_ID);
        Claim userRole = decodedJWT.getClaim(USER_ROLE);
        Claim timeStamp = decodedJWT.getClaim(TIME_STAMP);
        map.put(USER_ID, userId.asString());
        map.put(USER_ROLE, userRole.asString());
        map.put(TIME_STAMP, timeStamp.asLong().toString());

        return map;
    }
}
