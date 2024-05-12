package com.zhaomsdemo.research.datahub.service;

import com.zhaomsdemo.research.datahub.entity.UserEntity;
import com.zhaomsdemo.research.datahub.model.UserModel;
import com.zhaomsdemo.research.datahub.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserService {

    final RedisTemplate redisTemplate;
    final UserRepository userRepository;

    public void createUser(UserModel user) {
        user.setCreatedAt(Instant.now());
        redisTemplate.boundValueOps(user.getId()).set(user);
        userRepository.save(toUserEntity(user));
    }

    public UserModel getUser(String id) {
        return (UserModel) redisTemplate.boundValueOps(id).get();
    }

    private UserEntity toUserEntity(UserModel user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setBirthday(user.getBirthday());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    }
}
