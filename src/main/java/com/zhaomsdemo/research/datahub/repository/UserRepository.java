package com.zhaomsdemo.research.datahub.repository;

import com.zhaomsdemo.research.datahub.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
