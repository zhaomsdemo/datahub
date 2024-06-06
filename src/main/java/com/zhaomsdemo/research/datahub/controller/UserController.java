package com.zhaomsdemo.research.datahub.controller;

import com.zhaomsdemo.research.datahub.model.Result;
import com.zhaomsdemo.research.datahub.model.UserModel;
import com.zhaomsdemo.research.datahub.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    final UserService userService;

    @PostMapping("/user")
    public Result<UserModel> createNewUser(@RequestBody UserModel user) {
        return Result.success(userService.createUser(user));
    }

    @GetMapping("/user/{id}")
    public Result<UserModel> getUserById(@PathVariable String id) {
        return Result.success(userService.getUser(id));
    }

    @GetMapping("/users")
    public Result<List<UserModel>> getAllUsers() {
        return Result.success(userService.findAllUsers());
    }
}
