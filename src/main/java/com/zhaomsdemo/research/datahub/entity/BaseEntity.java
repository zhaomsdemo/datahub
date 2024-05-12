package com.zhaomsdemo.research.datahub.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @CreatedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @CreatedBy
    @Column
    private String createdUser;
    @LastModifiedBy
    @Column
    private String modifiedUser;
}
