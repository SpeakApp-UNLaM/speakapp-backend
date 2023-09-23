package com.guba.spring.speakappbackend.repositories.database.repositories;

import com.guba.spring.speakappbackend.repositories.database.models.Role;
import com.guba.spring.speakappbackend.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnum name);
}