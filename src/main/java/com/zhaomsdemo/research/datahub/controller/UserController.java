package com.zhaomsdemo.research.datahub.controller;

import com.zhaomsdemo.research.datahub.model.UserModel;
import com.zhaomsdemo.research.datahub.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    final UserService userService;

    @PostMapping("/")
    public void createNewUser(@RequestBody UserModel user) {
        userService.createUser(user);
    }

    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable String id) {
        return userService.getUser(id);
    }
}
