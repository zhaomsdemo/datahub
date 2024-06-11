package com.zhaomsdemo.research.datahub.config;

import com.zhaomsdemo.research.datahub.exception.TokenAuthExpiredException;
import com.zhaomsdemo.research.datahub.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static com.zhaomsdemo.research.datahub.constant.CommonConstant.TOKEN;
import static com.zhaomsdemo.research.datahub.constant.CommonConstant.USER_ID;

@Component
@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenUtil tokenUtil;
    @Value("${jwt.token.refreshTime}")
    private Long refreshTime;
    @Value("${jwt.token.refreshTime}")
    private Long expireTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("=======进入拦截器========");
        // 如果不是映射到方法直接通过,可以访问资源.
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //为空就返回错误
        String token = request.getHeader(TOKEN);
        if (null == token || "".equals(token.trim())) {
            return false;
        }
        log.info("==============token:" + token);
        Map<String, String> map = tokenUtil.parseToken(token);
        String userId = map.get(USER_ID);
        long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
        //1.判断 token 是否过期
        if (timeOfUse < refreshTime) {
            log.info("token验证成功");
            return true;
        } else if (timeOfUse >= refreshTime && timeOfUse < expireTime) {
            response.setHeader(TOKEN, tokenUtil.generateToken(userId));
            log.info("token刷新成功");
            return true;
        } else {
            throw new TokenAuthExpiredException("Token 过期");
        }
    }
}
