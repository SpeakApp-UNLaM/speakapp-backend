package com.guba.spring.speakappbackend.storages.database.repositories;

import com.guba.spring.speakappbackend.storages.database.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}