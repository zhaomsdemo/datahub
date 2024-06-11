package com.zhaomsdemo.research.datahub.controller;

import com.zhaomsdemo.research.datahub.dto.LoginDto;
import com.zhaomsdemo.research.datahub.model.Result;
import com.zhaomsdemo.research.datahub.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.zhaomsdemo.research.datahub.constant.CommonConstant.TOKEN;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {

    final TokenUtil tokenUtil;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDto login) {
        String token = tokenUtil.generateToken(login.getUsername());
        return Result.success(token);
    }

    @PostMapping("/validate")
    public Result<String> validateToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        tokenUtil.parseToken(token);
        return Result.success(token);
    }
}
