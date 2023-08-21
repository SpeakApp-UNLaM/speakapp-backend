package com.guba.spring.speakappbackend.database.repositories;

import com.guba.spring.speakappbackend.database.models.ImageTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageTempRepository extends JpaRepository<ImageTemp, Long> {

}