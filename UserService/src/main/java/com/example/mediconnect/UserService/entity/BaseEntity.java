package com.example.mediconnect.UserService.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
//    @GenericGenerator(name = "id", strategy = "uuid2")
//    @Type(type = "org.hibernate.type.UUIDCharType")
//    private UUID id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

}
