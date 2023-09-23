package com.guba.spring.speakappbackend.repositories.database.repositories;

import com.guba.spring.speakappbackend.repositories.database.models.ImageTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageTempRepository extends JpaRepository<ImageTemp, Long> {

}