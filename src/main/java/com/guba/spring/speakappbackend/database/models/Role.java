package com.guba.spring.speakappbackend.database.models;

import com.guba.spring.speakappbackend.database.converters.RoleJpaConverter;
import com.guba.spring.speakappbackend.enums.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_role")
@Setter
@Getter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;

    @Convert(converter = RoleJpaConverter.class)
    @Column(name = "name")
    private RoleEnum name;
}
