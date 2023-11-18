package com.guba.spring.speakappbackend.storages.database.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.guba.spring.speakappbackend.enums.RoleEnum.PATIENT;

@Setter
@Getter
@MappedSuperclass
public abstract class UserAbstract {

    @Transient//this field not exist in table
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image_data", columnDefinition = "TEXT")
    private String imageData;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="id_role", nullable=false)
    private Role role;

    public boolean isUserPatient() {
        return role.getName() == PATIENT;
    }
}
