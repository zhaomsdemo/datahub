package com.zhaomsdemo.research.datahub.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "T_USER")
public class UserEntity extends BaseEntity{
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String phone;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date birthday;
    @Column(nullable = false)
    String gender;
}
