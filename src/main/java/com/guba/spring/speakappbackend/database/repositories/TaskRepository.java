package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}