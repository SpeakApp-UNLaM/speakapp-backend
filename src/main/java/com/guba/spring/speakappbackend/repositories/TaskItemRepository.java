package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.TaskItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskItemRepository extends JpaRepository<TaskItem, Long> {

}