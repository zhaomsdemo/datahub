package com.zhaomsdemo.research.datahub.service;

import com.zhaomsdemo.research.datahub.entity.UserEntity;
import com.zhaomsdemo.research.datahub.exception.UserNotFoundException;
import com.zhaomsdemo.research.datahub.model.UserModel;
import com.zhaomsdemo.research.datahub.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserService {

    final RedisTemplate redisTemplate;
    final UserRepository userRepository;

    public UserModel createUser(UserModel user) {
        user.setCreatedAt(Instant.now());
//        redisTemplate.boundValueOps(user.getId()).set(user);
        UserEntity userEntity = userRepository.save(toUserEntity(user));
        return toUserModel(userEntity);
    }

    public UserModel getUser(String id) {
//        return (UserModel) redisTemplate.boundValueOps(id).get();
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return toUserModel(userEntity);
    }

    public List<UserModel> findAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();
        userModels = userEntities.stream().map(userEntity -> toUserModel(userEntity)).collect(Collectors.toUnmodifiableList());
        return userModels;
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

    private UserModel toUserModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setName(userEntity.getName());
        userModel.setEmail(userEntity.getEmail());
        userModel.setBirthday(userEntity.getBirthday());
        userModel.setPhone(userEntity.getPhone());
        userModel.setGender(userEntity.getGender());
        return userModel;
    }
}
